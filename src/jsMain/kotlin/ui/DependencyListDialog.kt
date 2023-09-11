package ui

import mui.material.Dialog
import mui.material.DialogTitle
import mui.material.Link
import mui.material.LinkUnderline
import mui.material.List
import mui.material.ListItem
import mui.material.ListItemButton
import mui.material.Stack
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.useRequiredContext
import web.cssom.px
import web.window.window
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
            sx {
                width = 600.px
            }
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
                                +dep.description
                            }
                            Link {
                                underline = LinkUnderline.always
                                onClick = {
                                    it.stopPropagation()
                                    window.open(dep.url)
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
