package ui

import mui.icons.material.Brightness4
import mui.icons.material.Brightness7
import mui.icons.material.Code
import mui.icons.material.CodeOff
import mui.icons.material.GitHub
import mui.icons.material.MenuBook
import mui.icons.material.Diamond
import mui.material.Box
import mui.material.IconButton
import mui.material.Stack
import mui.material.StackDirection
import mui.system.responsive
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.useRequiredContext
import web.cssom.Position
import web.cssom.px
import web.window.window

val TopMenu = FC<Props> {
    var showVersions by useRequiredContext(ShowVersionContext)
    var theme by useRequiredContext(ThemeContext)
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

            IconButton {
                onClick = {
                    window.open("https://www.jetbrains.com/lp/compose-multiplatform/")
                }
                title = "View compose documentation"
                MenuBook()
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

            IconButton {
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
        }
    }
}
