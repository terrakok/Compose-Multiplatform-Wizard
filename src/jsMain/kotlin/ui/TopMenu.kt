package ui

import mui.icons.material.*
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.ReactHTML
import web.cssom.Position
import web.cssom.px
import web.window.window
import wizard.WizardType

val TopMenu = FC<Props> {
    var showVersions by useRequired(ShowVersionContext)
    var theme by useRequired(ThemeContext)
    val wizardType by useRequired(WizardTypeContext)
    Box {
        sx {
            position = Position.relative
        }
        Stack {
            direction = responsive(StackDirection.row)
            sx {
                position = Position.absolute
                right = 0.px
                top = 0.px
            }

            IconButton {
                onClick = {
                    showVersions = !showVersions
                }
                if (showVersions) {
                    title = "Hide versions"
                    Code()
                } else {
                    title = "Show versions"
                    CodeOff()
                }
            }

            when (wizardType) {
                WizardType.ComposeApp -> IconButton {
                    onClick = {
                        window.open("https://www.jetbrains.com/lp/compose-multiplatform/")
                    }
                    title = "View compose documentation"
                    MenuBook()
                }

                WizardType.KmpLibrary -> IconButton {
                    onClick = {
                        window.open("https://kotlinlang.org/docs/multiplatform-get-started.html")
                    }
                    title = "View KMP documentation"
                    MenuBook()
                }
            }

            IconButton {
                onClick = {
                    window.open("https://github.com/terrakok/Compose-Multiplatform-Wizard")
                }
                title = "Fork me on GitHub"
                GitHub()
            }

            IconButton {
                onClick = {
                    theme = if (theme == Themes.Light) Themes.Dark else Themes.Light
                }
                if (theme == Themes.Light) {
                    title = "Use dark theme"
                    Brightness7()
                } else {
                    title = "Use light theme"
                    Brightness4()
                }
            }

            IconButton {
                onClick = {
                    window.open("https://github.com/terrakok/kmp-awesome#contents")
                }
                title = "More KMP libraries"
                Diamond()
            }

            when (wizardType) {
                WizardType.ComposeApp -> IconButton {
                    onClick = {
                        window.open("https://terrakok.github.io/kmp-web-wizard/")
                    }
                    title = "KMP Library Wizard"
                    ReactHTML.img {
                        src = "kotlin-logo.svg"
                        width = 24.0
                        height = 24.0
                    }
                }

                WizardType.KmpLibrary -> IconButton {
                    onClick = {
                        window.open("https://terrakok.github.io/Compose-Multiplatform-Wizard/")
                    }
                    title = "Compose Multiplatform Wizard"
                    ReactHTML.img {
                        src = "compose-logo.svg"
                        width = 24.0
                        height = 24.0
                    }
                }
            }
        }
    }
}
