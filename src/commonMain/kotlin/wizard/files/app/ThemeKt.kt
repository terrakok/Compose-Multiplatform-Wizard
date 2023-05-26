package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class ThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/Theme.kt"
    override val content = """
        package ${info.packageId}

        import androidx.compose.foundation.isSystemInDarkTheme
        import androidx.compose.material3.lightColorScheme
        import androidx.compose.material3.darkColorScheme
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.Surface
        import androidx.compose.runtime.Composable

        private val LightColorScheme = lightColorScheme(
            primary = md_theme_light_primary,
            onPrimary = md_theme_light_onPrimary,
            secondary = md_theme_light_secondary,
            onSecondary = md_theme_light_onSecondary,
            error = md_theme_light_error,
            onError = md_theme_light_onError,
            background = md_theme_light_background,
            onBackground = md_theme_light_onBackground,
            surface = md_theme_light_surface,
            onSurface = md_theme_light_onSurface,
        )

        private val DarkColorScheme = darkColorScheme(
            primary = md_theme_dark_primary,
            onPrimary = md_theme_dark_onPrimary,
            secondary = md_theme_dark_secondary,
            onSecondary = md_theme_dark_onSecondary,
            error = md_theme_dark_error,
            onError = md_theme_dark_onError,
            background = md_theme_dark_background,
            onBackground = md_theme_dark_onBackground,
            surface = md_theme_dark_surface,
            onSurface = md_theme_dark_onSurface,
        )

        @Composable
        internal fun AppTheme(
            useDarkTheme: Boolean = isSystemInDarkTheme(),
            content: @Composable() () -> Unit
        ) {
            val colors = if (!useDarkTheme) {
                LightColorScheme
            } else {
                DarkColorScheme
            }

            MaterialTheme(
                colorScheme = colors,
                typography = Typography,
                shapes = Shapes,
                content = {
                    Surface(content = content)
                }
            )
        }

    """.trimIndent()
}