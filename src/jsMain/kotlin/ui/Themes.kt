package ui

import js.objects.unsafeJso
import mui.material.PaletteMode.Companion.dark
import mui.material.PaletteMode.Companion.light
import mui.material.styles.createTheme

object Themes {
    val Light = createTheme(
        unsafeJso {
            palette = unsafeJso { mode = light }
        }
    )

    val Dark = createTheme(
        unsafeJso {
            palette = unsafeJso { mode = dark }
        }
    )
}