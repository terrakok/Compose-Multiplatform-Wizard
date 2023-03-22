import csstype.AlignItems
import csstype.Padding
import csstype.px
import mui.icons.material.*
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.img
import react.dom.onChange
import web.dom.document
import web.html.HTML
import web.html.HTMLInputElement

fun main() {
    val root = document.createElement(HTML.div)
        .also { document.body.appendChild(it) }
    createRoot(root).render(App.create())
}

val App = FC<Props> {
    ThemeModule {
        Container {
            sx {
                padding = Padding(24.px, 24.px)
                minWidth = 650.px
            }
            Paper {
                var projectName by useState("ComposeApp")
                val withAndroid = useState(true)
                val withIPhone = useState(true)
                val withDesktop = useState(true)

                sx {
                    padding = Padding(24.px, 24.px)
                }

                Stack {
                    direction = responsive(StackDirection.column)
                    spacing = responsive(2)
                    sx {
                        alignItems = AlignItems.center
                    }

                    Stack {
                        direction = responsive(StackDirection.row)
                        spacing = responsive(2)
                        sx {
                            alignItems = AlignItems.center
                        }
                        img {
                            src = "compose-logo.svg"
                            width = 150.0
                            height = 150.0
                        }
                        Typography {
                            variant = TypographyVariant.h3
                            +"Compose Multiplatform Wizard"
                        }
                    }

                    TextField {
                        label = ReactNode("Project name")
                        sx {
                            width = 424.px
                        }
                        value = projectName
                        onChange = { event ->
                            projectName = (event.target as HTMLInputElement).value
                        }
                    }

                    ButtonGroup {
                        disableElevation = true
                        TargetButton {
                            selection = withAndroid
                            icon = Android
                            title = "Android"
                        }
                        TargetButton {
                            selection = withIPhone
                            icon = Apple
                            title = "iPhone"
                        }
                        TargetButton {
                            selection = withDesktop
                            icon = Laptop
                            title = "Desktop"
                        }
                    }

                    Button {
                        variant = ButtonVariant.contained
                        size = Size.large
                        startIcon = ArrowCircleDown.create()
                        +"Download"

                        val b1 by withAndroid
                        val b2 by withIPhone
                        val b3 by withDesktop
                        disabled = projectName.isBlank() || (!b1 && !b2 && !b3)
                    }
                }
            }
        }
    }
}
