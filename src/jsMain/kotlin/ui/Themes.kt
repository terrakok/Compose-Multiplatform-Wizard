package ui

import js.core.jso
import mui.material.PaletteMode.Companion.dark
import mui.material.PaletteMode.Companion.light
import mui.material.styles.createTheme

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