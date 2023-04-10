package ui

import csstype.px
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.StateInstance
import react.useRequiredContext
import wizard.libraryNotation

external interface DependencyListDialogProps : Props {
    var dependencyBox: DependencyBox
    var open: Boolean
    var onClose: (Int) -> Unit
}

val DependencyListDialog = FC<DependencyListDialogProps> { props ->
    val dependencyBox = props.dependencyBox
    val open = props.open
    val onClose = props.onClose

    Dialog {
        this.open = open
        DialogTitle { +"Select Dependency" }
        List {
            dependencyBox.dependencies.forEachIndexed { i, dep ->
                ListItemButton {
                    onClick = {
                        onClose(i)
                    }
                    key = dep.libraryNotation
                    ListItem {
                        disableGutters = true
                        val showVersion by useRequiredContext(ShowVersionContext)
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
                                sx {
                                    height = 50.px
                                }
                                +dep.description
                            }
                        }
                    }
                }
            }
        }
    }
}
