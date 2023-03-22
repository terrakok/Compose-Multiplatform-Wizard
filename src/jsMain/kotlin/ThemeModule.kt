import js.core.jso
import mui.material.CssBaseline
import mui.material.PaletteMode.dark
import mui.material.PaletteMode.light
import mui.material.styles.Theme
import mui.material.styles.ThemeProvider
import mui.material.styles.createTheme
import react.*

typealias ThemeState = StateInstance<Theme>

val ThemeContext = createContext<ThemeState>()

val ThemeModule = FC<PropsWithChildren> { props ->
    val state = useState(Themes.Dark)
    val (theme) = state

    ThemeContext(state) {
        ThemeProvider {
            this.theme = theme

            CssBaseline()
            +props.children
        }
    }
}


object Themes {
    val Light = createTheme(
        jso {
            palette = jso { mode = light }
        }
    )

    val Dark = createTheme(
        jso {
            palette = jso { mode = dark }
        }
    )
}