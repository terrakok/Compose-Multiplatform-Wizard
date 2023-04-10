package ui

import csstype.*
import mui.icons.material.CheckCircleRounded
import mui.icons.material.Edit
import mui.icons.material.RadioButtonUncheckedRounded
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import web.window.window
import wizard.Dependency

external interface DependencyCardProps : Props {
    var dependency: DependencyBox
}

val DependencyCard = FC<DependencyCardProps> { props ->
    val dep by props.dependency.selectedDep
    var isSelected by props.dependency.isSelected
    val showVersion by useRequiredContext(ShowVersionContext)
    var isDialogOpen by useState(false)

    Card {
        sx {
            width = 320.px
        }
        onClick = {
            isSelected = !isSelected
        }
        CardActionArea {
            Box {
                sx {
                    position = Position.relative
                }
                Checkbox {
                    sx {
                        position = Position.absolute
                        right = 10.px
                        top = 10.px
                    }
                    icon = RadioButtonUncheckedRounded.create()
                    checkedIcon = CheckCircleRounded.create()
                    checked = isSelected
                }
            }
            Stack {
                sx {
                    padding = 16.px
                    paddingBottom = 8.px
                }
                spacing = responsive(1)
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

                Stack {
                    direction = responsive(StackDirection.row)
                    sx {
                        justifyContent = JustifyContent.spaceBetween
                        alignItems = AlignItems.center
                    }

                    Typography {
                        variant = TypographyVariant.body1
                        if (showVersion) {
                            +dep.version
                        }
                    }
                    Stack {
                        direction = responsive(StackDirection.row)
                        if (props.dependency.isMultiSelect) {
                            Button {
                                sx {
                                    minWidth = 20.px
                                }
                                onClick = {
                                    it.stopPropagation()
                                    isDialogOpen = !isDialogOpen
                                }
                                DependencyListDialog {
                                    dependencyBox = props.dependency
                                    open = isDialogOpen
                                    onClose = { i ->
                                        props.dependency.selectIndex(i)
                                        isDialogOpen = !isDialogOpen
                                    }
                                }
                                Edit {
                                    sx {
                                        width = 18.px
                                        height = 18.px
                                    }
                                }
                            }
                        }
                        Button {
                            sx {
                                right = -5.px
                            }
                            onClick = {
                                it.stopPropagation()
                                window.open(dep.url)
                            }
                            +"More info"
                        }
                    }
                }
            }
        }
    }
}