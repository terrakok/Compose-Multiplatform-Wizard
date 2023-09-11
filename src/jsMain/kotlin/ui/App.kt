package ui

import mui.material.CssBaseline
import mui.material.styles.Theme
import mui.material.styles.ThemeProvider
import react.FC
import react.Props
import react.StateInstance
import react.createContext
import react.useState
import wizard.ProjectInfo

external interface AppProps : Props {
    var generate: (ProjectInfo) -> Unit
}

val ShowVersionContext = createContext<StateInstance<Boolean>>()
val ThemeContext = createContext<StateInstance<Theme>>()

val App = FC<AppProps> { props ->
    ThemeContext.Provider {
        val currentThemeIsDark = kotlinx.browser.window.matchMedia("(prefers-color-scheme: dark)").matches
        val state = useState(
            if (currentThemeIsDark) Themes.Dark else Themes.Light
        )
        value = state

        ThemeProvider {
            theme = state.component1()
            CssBaseline()

            Content {
                generate = props.generate
            }
        }
    }
}
