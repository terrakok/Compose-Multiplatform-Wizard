package ui

import mui.material.Stack
import mui.material.StackDirection
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML
import web.cssom.AlignItems
import web.cssom.px

external interface HeaderProps : Props {
    var image: String
    var title: String
}

val Header = FC<HeaderProps> { props ->
    Stack {
        direction = responsive(StackDirection.row)
        spacing = responsive(2)
        sx {
            alignItems = AlignItems.center
            paddingTop = 24.px
            paddingBottom = 24.px
        }
        ReactHTML.img {
            src = props.image
            width = 150.0
            height = 150.0
        }
        Typography {
            variant = TypographyVariant.h3
            +props.title
        }
    }
}