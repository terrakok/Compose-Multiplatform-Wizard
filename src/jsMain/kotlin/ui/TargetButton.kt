package ui

import mui.icons.material.CheckCircleRounded
import mui.icons.material.RadioButtonUncheckedRounded
import mui.icons.material.SvgIconComponent
import mui.material.Badge
import mui.material.BadgeColor
import mui.material.BadgeVariant
import mui.material.Box
import mui.material.Button
import mui.material.ButtonVariant
import mui.material.Stack
import mui.material.StackDirection
import mui.material.SvgIconColor
import mui.material.Tooltip
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.StateInstance
import web.cssom.AlignItems
import web.cssom.Position
import web.cssom.px
import web.cssom.unaryMinus

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