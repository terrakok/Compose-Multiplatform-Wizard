import csstype.AlignItems
import csstype.px
import mui.icons.material.SvgIconComponent
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.StateInstance

external interface TargetButtonProps : Props {
    var icon: SvgIconComponent
    var title: String
    var selection: StateInstance<Boolean>
}

val TargetButton = FC<TargetButtonProps> { props ->
    var isSelected by props.selection
    Button {
        variant = if (isSelected) {
            ButtonVariant.contained
        } else {
            ButtonVariant.outlined
        }
        onClick = {
            isSelected = !isSelected
        }
        Stack {
            direction = responsive(StackDirection.column)
            spacing = responsive(2)
            sx {
                alignItems = AlignItems.center
                minWidth = 110.px
            }
            props.icon {
                color = if (isSelected) {
                    SvgIconColor.inherit
                } else {
                    SvgIconColor.primary
                }
                width = 40.0
                height = 40.0
            }
            Typography {
                variant = TypographyVariant.body2
                +props.title
            }
        }
    }
}