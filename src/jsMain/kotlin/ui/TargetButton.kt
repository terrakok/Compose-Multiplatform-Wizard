package ui

import csstype.AlignItems
import csstype.Position
import csstype.px
import csstype.unaryMinus
import mui.icons.material.CheckCircleRounded
import mui.icons.material.RadioButtonUncheckedRounded
import mui.icons.material.SvgIconComponent
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*

external interface TargetButtonProps : Props {
    var icon: SvgIconComponent
    var title: String
    var selection: StateInstance<Boolean>
    var status: String?
}

val TargetButton = FC<TargetButtonProps> { props ->
    var isSelected by props.selection

    Tooltip {
        props.status?.let { title = ReactNode(it) }
        Button {
            variant = ButtonVariant.outlined
            onClick = {
                isSelected = !isSelected
            }
            Box {
                Box {
                    sx {
                        position = Position.relative
                    }
                    if (isSelected) {
                        CheckCircleRounded {
                            sx {
                                position = Position.absolute
                                right = -8.px
                                top = 0.px
                            }
                        }
                    } else {
                        RadioButtonUncheckedRounded {
                            sx {
                                position = Position.absolute
                                right = -8.px
                                top = 0.px
                            }
                        }
                    }
                }
                Stack {
                    direction = responsive(StackDirection.column)
                    spacing = responsive(1)
                    sx {
                        padding = 10.px
                        alignItems = AlignItems.center
                        minWidth = 110.px
                    }
                    Badge {
                        variant = BadgeVariant.dot
                        color = BadgeColor.warning
                        invisible = props.status == null
                        props.icon {
                            color = if (isSelected) {
                                SvgIconColor.inherit
                            } else {
                                SvgIconColor.primary
                            }
                            width = 60.0
                            height = 60.0
                        }
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        +props.title
                    }
                }
            }
        }
    }
}