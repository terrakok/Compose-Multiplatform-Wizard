package ui

import csstype.Position
import csstype.px
import csstype.unaryMinus
import mui.icons.material.CheckBoxOutlineBlankSharp
import mui.icons.material.CheckBoxSharp
import mui.icons.material.CheckCircleRounded
import mui.icons.material.RadioButtonUncheckedRounded
import mui.icons.material.RadioButtonUncheckedSharp
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.StateInstance
import react.create
import react.useRequiredContext
import web.window.window
import wizard.Dependency
import wizard.libraryNotation

external interface DependencyListDialogProps : Props {
    var dependencies: List<Dependency>
    var selectedDependencies: List<Dependency>
    var allowMultiSelect: Boolean
    var open: Boolean
    var onSelection: (Int) -> Unit
    var onClose: () -> Unit
}

val DependencyListDialog = FC<DependencyListDialogProps> { props ->
    val dependencies = props.dependencies
    val selectedDependencies = props.selectedDependencies
    val allowMultiSelect = props.allowMultiSelect
    val open = props.open
    val onSelection = props.onSelection
    val onClose = props.onClose

    Dialog {
        this.open = open

        this.onClick = {
            it.stopPropagation()
        }
        this.onClose = { _, _ ->
            println("dialog dismissed")
//            onClose()
        }
        this.onBackdropClick = {
            println("backdrop click")
        }

        DialogTitle { +"Select Dependency" }
        Button {
            onClick = {
                println("Click on close button")
                onClose()
            }
            +"Close"
        }

        List {
            sx {
                width = 600.px
            }
            dependencies.forEachIndexed { i, dep ->
                val handleItemClick = {
                    onSelection(i)
                    if(allowMultiSelect) {
                        println("multi-select dialog, keep open. Click on $i")
                    } else {
                        onClose()
                    }
                }

                ListItemButton {
                    onClick = {
                        it.preventDefault()
                        it.stopPropagation()
                        handleItemClick()
                    }
                    key = dep.libraryNotation
                    ListItem {
                        disableGutters = true
                        val showVersion by useRequiredContext(ShowVersionContext)

                        if(allowMultiSelect) {
                            // with multi-select, show the checkbox as squares for multi-select list
                            Checkbox {
                                sx {
                                    position = Position.absolute
                                    right = 10.px
                                    top = 10.px
                                }
                                onClick = null
                                icon = CheckBoxOutlineBlankSharp.create()
                                checkedIcon = CheckBoxSharp.create()
                                checked = dep in selectedDependencies
                            }
                        } else {
                            // with single-select, show the checkbox as round for radio button selection
                            Checkbox {
                                sx {
                                    position = Position.absolute
                                    right = 10.px
                                    top = 10.px
                                }
                                onClick = null
                                icon = RadioButtonUncheckedRounded.create()
                                checkedIcon = CheckCircleRounded.create()
                                checked = dep in selectedDependencies
                            }
                        }

                        Stack {
                            Typography {
                                variant = TypographyVariant.h5
                                +dep.title
                                if (showVersion) {
                                    +" (${dep.version})"
                                }
                            }
                            Typography {
                                variant = TypographyVariant.body2
                                +dep.description
                            }
                            Link {
                                underline = LinkUnderline.always
                                onClick = {
                                    it.stopPropagation()
//                                    window.open(dep.url)
                                }
                                +dep.url
                            }
                        }
                    }
                }
            }
        }
    }
}
