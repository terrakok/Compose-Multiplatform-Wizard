package ui

import emotion.react.css
import js.objects.unsafeJso
import js.reflect.unsafeCast
import mui.material.*
import mui.material.Size
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import npm.SVG
import npm.Svg
import react.*
import react.dom.html.ReactHTML
import react.dom.onChange
import web.cssom.*
import web.html.*
import wizard.AppIcon
import wizard.AppIconBackground
import wizard.AppIconSymbolStyle
import wizard.drawAppIcon

/** Icon coordinate space, mirroring the size used by `SvgIconRenderer`. */
private const val PREVIEW_SIZE = 104.0

private val SWATCH_SIZE = 26.px
private val SWATCH_BORDER = Color("#00000040")

external interface AppIconPreviewProps : Props {
    var iconSpec: AppIcon
    var onIconSpecChange: (AppIcon) -> Unit
}

val AppIconPreview = FC<AppIconPreviewProps> { props ->
    val iconSpec = props.iconSpec
    val onIconSpecChange = props.onIconSpecChange
    val containerRef = useRef<HTMLDivElement>(null)
    val canvasRef = useRef<Svg>(null)
    var pickerAnchor by useState<HTMLButtonElement?>(null)

    val symbol = materialSymbolOrNull(iconSpec.symbolName)
    val symbolComponent = symbol?.component(iconSpec.symbolStyle)

    useEffect(iconSpec) {
        val container = containerRef.current ?: return@useEffect
        val canvas = canvasRef.current ?: SVG()
            .addTo(container)
            .size(PREVIEW_SIZE, PREVIEW_SIZE)
            .viewbox(0.0, 0.0, PREVIEW_SIZE, PREVIEW_SIZE)
            .also { canvasRef.current = it }

        canvas.clear()
        canvas.drawAppIcon(iconSpec, PREVIEW_SIZE)
    }

    Box {
        sx {
            border = Border(1.px, LineStyle.solid, Color("#BDBDBD"))
            borderRadius = 4.px
            display = Display.flex
            alignItems = AlignItems.stretch
            padding = Padding(10.px, 12.px)
            width = 565.px
        }

        // Preview column
        Stack {
            sx {
                width = 116.px
                flexShrink = number(0.0)
                alignItems = AlignItems.center
                justifyContent = JustifyContent.center
                marginRight = 12.px
            }
            spacing = responsive(1)

            ReactHTML.div {
                css {
                    position = Position.relative
                    width = PREVIEW_SIZE.px
                    height = PREVIEW_SIZE.px
                    display = Display.flex
                    alignItems = AlignItems.center
                    justifyContent = JustifyContent.center
                }

                ReactHTML.div {
                    ref = containerRef
                    css {
                        position = Position.absolute
                        left = 0.px
                        top = 0.px
                    }
                }
            }
        }

        Divider {
            orientation = Orientation.vertical
            flexItem = true
        }

        // Editor column
        Stack {
            sx {
                flexGrow = number(1.0)
                marginLeft = 12.px
                justifyContent = JustifyContent.center
            }
            spacing = responsive(1)

            // Symbol: the Material icon, its color and its style
            EditorRow {
                Button {
                    variant = ButtonVariant.outlined
                    size = Size.small
                    sx {
                        flexShrink = number(0.0)
                        justifyContent = JustifyContent.flexStart
                    }
                    startIcon = symbolComponent?.create()
                    onClick = { event -> pickerAnchor = event.currentTarget }

                    Typography {
                        variant = TypographyVariant.body2
                        noWrap = true
                        sx { textTransform = None.none }
                        +iconSpec.symbolName
                    }
                }

                SymbolPicker {
                    anchor = pickerAnchor
                    style = iconSpec.symbolStyle
                    selectedName = iconSpec.symbolName
                    onClose = { pickerAnchor = null }
                    onSelect = { newName ->
                        pickerAnchor = null
                        onIconSpecChange(iconSpec.copy(symbolName = newName))
                    }
                }

                ButtonGroup {
                    disableElevation = true
                    size = Size.small

                    AppIconSymbolStyle.entries.forEach { style ->
                        Button {
                            variant = if (iconSpec.symbolStyle == style) {
                                ButtonVariant.contained
                            } else {
                                ButtonVariant.outlined
                            }
                            onClick = {
                                onIconSpecChange(iconSpec.copy(symbolStyle = style))
                            }
                            +style.name
                        }
                    }
                }

                ColorCircle {
                    hint = "Symbol color"
                    color = iconSpec.symbolColor
                    onColorChange = { newColor ->
                        onIconSpecChange(iconSpec.copy(symbolColor = newColor))
                    }
                }
            }

            // Symbol size, corner rounding and the shadow switch
            EditorRow {
                val scaleValue = iconSpec.symbolScale
                Typography {
                    variant = TypographyVariant.caption
                    sx { color = Color("#757575") }
                    +"Scale"
                }

                Slider {
                    size = Size.small
                    min = 0.3
                    max = 1.5
                    step = 0.01
                    getAriaValueText = { _, _ -> scaleValue.toString() }
                    valueLabelDisplay = "auto"
                    value = scaleValue
                    onChange = { _, newValue, _ ->
                        onIconSpecChange(iconSpec.copy(symbolScale = newValue.unsafeCast<Number>().toFloat()))
                    }
                }

                val radiusValue = iconSpec.cornerRadiusPercent
                Typography {
                    variant = TypographyVariant.caption
                    sx { color = Color("#757575") }
                    +"Radius"
                }

                Slider {
                    size = Size.small
                    min = 0.0
                    max = 50.0
                    step = 1.0
                    getAriaValueText = { _, _ -> radiusValue.toString() }
                    valueLabelDisplay = "auto"
                    value = radiusValue
                    onChange = { _, newValue, _ ->
                        onIconSpecChange(iconSpec.copy(cornerRadiusPercent = newValue.unsafeCast<Number>().toInt()))
                    }
                }
            }

            // Background
            EditorRow {
                ButtonGroup {
                    disableElevation = true
                    size = Size.small

                    Button {
                        variant = if (iconSpec.background is AppIconBackground.Solid) {
                            ButtonVariant.contained
                        } else {
                            ButtonVariant.outlined
                        }
                        onClick = {
                            onIconSpecChange(iconSpec.copy(background = iconSpec.background.toSolid()))
                        }
                        +"Solid"
                    }
                    Button {
                        variant = if (iconSpec.background is AppIconBackground.Gradient) {
                            ButtonVariant.contained
                        } else {
                            ButtonVariant.outlined
                        }
                        onClick = {
                            onIconSpecChange(iconSpec.copy(background = iconSpec.background.toGradient()))
                        }
                        +"Gradient"
                    }
                }

                when (val background = iconSpec.background) {
                    is AppIconBackground.Solid -> {
                        ColorCircle {
                            hint = "Background color"
                            color = background.color
                            onColorChange = { newColor ->
                                onIconSpecChange(iconSpec.copy(background = AppIconBackground.Solid(newColor)))
                            }
                        }
                    }

                    is AppIconBackground.Gradient -> {
                        ColorCircle {
                            hint = "Gradient start"
                            color = background.from
                            onColorChange = { newColor ->
                                onIconSpecChange(iconSpec.copy(background = background.copy(from = newColor)))
                            }
                        }

                        ColorCircle {
                            hint = "Gradient end"
                            color = background.to
                            onColorChange = { newColor ->
                                onIconSpecChange(iconSpec.copy(background = background.copy(to = newColor)))
                            }
                        }

                        Typography {
                            variant = TypographyVariant.caption
                            sx { color = Color("#757575") }
                            +"Angle"
                        }

                        Slider {
                            size = Size.small
                            min = -180.0
                            max = 180.0
                            step = 1.0
                            getAriaValueText = { _, _ -> background.angleDegrees.toString() }
                            valueLabelDisplay = "auto"
                            value = background.angleDegrees
                            onChange = { _, newValue, _ ->
                                onIconSpecChange(iconSpec.copy(background = background.copy(angleDegrees = newValue.unsafeCast<Number>().toInt())))
                            }
                        }
                    }
                }
            }
        }
    }
}

private external interface SymbolPickerProps : Props {
    var anchor: HTMLButtonElement?
    var style: AppIconSymbolStyle
    var selectedName: String
    var onSelect: (String) -> Unit
    var onClose: () -> Unit
}

/** Popup with the Material icon catalog, filtered by icon name. */
private val SymbolPicker = FC<SymbolPickerProps> { props ->
    var query by useState("")

    // The picker stays mounted for the closing animation, so the filter is dropped by hand.
    useEffect(props.anchor) {
        if (props.anchor == null) {
            query = ""
        }
    }

    Popover {
        open = props.anchor != null
        anchorEl = props.anchor
        onClose = { _, _ -> props.onClose() }
        anchorOrigin = unsafeJso {
            vertical = unsafeCast("bottom")
            horizontal = unsafeCast("left")
        }

        Stack {
            sx {
                width = 324.px
                padding = 8.px
            }
            spacing = responsive(1)

            TextField {
                autoFocus = true
                size = Size.small
                fullWidth = true
                placeholder = "Filter by name"
                value = query
                onChange = { event ->
                    query = (event.target as HTMLInputElement).value
                }
            }

            val matches = MaterialSymbols.filter { it.name.contains(query, ignoreCase = true) }

            Box {
                sx {
                    display = Display.flex
                    flexWrap = FlexWrap.wrap
                    gap = 2.px
                    maxHeight = 232.px
                    overflowY = Auto.auto
                }

                matches.forEach { symbol ->
                    IconButton {
                        title = symbol.name
                        sx {
                            width = 34.px
                            height = 34.px
                            borderRadius = 6.px
                            if (symbol.name == props.selectedName) {
                                backgroundColor = Color("#3B82F633")
                            }
                        }
                        onClick = { props.onSelect(symbol.name) }

                        symbol.component(props.style)()
                    }
                }

                if (matches.isEmpty()) {
                    Typography {
                        variant = TypographyVariant.caption
                        sx { color = Color("#757575") }
                        +"No icon matches \"$query\""
                    }
                }
            }
        }
    }
}

/** A single line of the editor, keeping every control vertically aligned. */
private val EditorRow = FC<PropsWithChildren> { props ->
    Stack {
        direction = responsive(StackDirection.row)
        spacing = responsive(1)
        sx { alignItems = AlignItems.center }
        +props.children
    }
}

private external interface ColorCircleProps : Props {
    var hint: String
    var color: String
    var onColorChange: (String) -> Unit
}

/**
 * Color picker shown as a plain circle: a native color input, with its swatch
 * (drawn by vendor pseudo elements) reshaped into a circle.
 */
private val ColorCircle = FC<ColorCircleProps> { props ->
    Tooltip {
        title = ReactNode(props.hint)

        ReactHTML.input {
            type = InputType.color
            value = props.color
            onChange = { event ->
                props.onColorChange((event.target as HTMLInputElement).value)
            }
            css {
                flexShrink = number(0.0)
                width = SWATCH_SIZE
                height = SWATCH_SIZE
                padding = 0.px
                border = Border(0.px, LineStyle.hidden)
                borderRadius = 50.pct
                background = None.none
                cursor = Cursor.pointer
                "&::-webkit-color-swatch-wrapper" {
                    padding = 0.px
                }
                "&::-webkit-color-swatch" {
                    border = Border(1.px, LineStyle.solid, SWATCH_BORDER)
                    borderRadius = 50.pct
                }
                "&::-moz-color-swatch" {
                    border = Border(1.px, LineStyle.solid, SWATCH_BORDER)
                    borderRadius = 50.pct
                }
            }
        }
    }
}

private fun AppIconBackground.toSolid(): AppIconBackground.Solid = when (this) {
    is AppIconBackground.Solid -> this
    is AppIconBackground.Gradient -> AppIconBackground.Solid(from)
}

private fun AppIconBackground.toGradient(): AppIconBackground.Gradient = when (this) {
    is AppIconBackground.Solid -> AppIconBackground.Gradient(color, color.darken(0.7), 45)
    is AppIconBackground.Gradient -> this
}

/** Keeps the gradient visible right after switching from a solid background. */
private fun String.darken(factor: Double): String {
    val channels = removePrefix("#")
        .takeIf { it.length == 6 }
        ?.chunked(2)
        ?.mapNotNull { it.toIntOrNull(16) }
        ?.takeIf { it.size == 3 }
        ?: return this

    return channels.joinToString(prefix = "#", separator = "") { channel ->
        (channel * factor).toInt().coerceIn(0, 255).toString(16).padStart(2, '0')
    }
}
