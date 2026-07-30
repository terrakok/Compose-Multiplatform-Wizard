package ui

import mui.material.CssBaseline
import mui.material.styles.Theme
import react.*
import wizard.*

external interface AppProps : Props {
    var restored: ProjectInfo?
    var save: (ProjectInfo) -> Unit
    var generate: (ProjectInfo) -> Unit
    var wizardType: WizardType
}

val ShowVersionContext = createContext<StateInstance<Boolean>>()
val ThemeContext = createContext<StateInstance<Theme>>()
val WizardTypeContext = createContext<StateInstance<WizardType>>()

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

            WizardTypeContext.Provider {
                val wizardType = useState(props.wizardType)
                value = wizardType

                when (wizardType.component1()) {
                    WizardType.ComposeApp -> ComposeAppWizardContent {
                        restored = props.restored
                        save = props.save
                        generate = props.generate
                    }

                    WizardType.KmpLibrary -> KmpLibraryWizardContent {
                        restored = props.restored
                        save = props.save
                        generate = props.generate
                    }
                }
            }
        }
    }
}
