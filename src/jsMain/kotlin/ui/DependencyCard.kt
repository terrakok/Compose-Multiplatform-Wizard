package ui

import csstype.*
import mui.icons.material.CheckCircleRounded
import mui.icons.material.RadioButtonUncheckedRounded
import mui.icons.material.WidthFull
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import web.window.window
import wizard.Dependency

external interface DependencyCardProps : Props {
    var dependency: Dependency
    var selection: StateInstance<Boolean>
}

val DependencyCard = FC<DependencyCardProps> { props ->
    val dep = props.dependency
    var isSelected by props.selection
    Card {
        sx {
            width = 320.px
        }
        CardContent {
            onClick = {
                isSelected = !isSelected
            }
            Box {
                sx {
                    position = Position.relative
                }
                Checkbox {
                    sx {
                        position = Position.absolute
                        right = -5.px
                        top = -5.px
                    }
                    icon = RadioButtonUncheckedRounded.create()
                    checkedIcon = CheckCircleRounded.create()
                    checked = isSelected
                    onChange = { _, value ->
                        isSelected = value
                    }
                }
            }
            Typography {
                variant = TypographyVariant.h5
                +dep.title
            }
            Typography {
                variant = TypographyVariant.body2
                sx {
                    height = 50.px
                }
                +dep.description
            }
        }
        CardActions {
            Button {
                onClick = { window.open(dep.url) }
                +"More info"
            }
        }
    }
}