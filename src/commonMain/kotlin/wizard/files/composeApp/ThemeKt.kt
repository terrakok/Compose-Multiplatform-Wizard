package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class ThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/kotlin/${info.packagePath}/theme/Theme.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.foundation.isSystemInDarkTheme
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.Surface
        import androidx.compose.material3.darkColorScheme
        import androidx.compose.material3.lightColorScheme
        import androidx.compose.runtime.*

        private val LightColorScheme = lightColorScheme(
            primary = PrimaryLight,
            onPrimary = OnPrimaryLight,
            primaryContainer = PrimaryContainerLight,
            onPrimaryContainer = OnPrimaryContainerLight,
            secondary = SecondaryLight,
            onSecondary = OnSecondaryLight,
            secondaryContainer = SecondaryContainerLight,
            onSecondaryContainer = OnSecondaryContainerLight,
            tertiary = TertiaryLight,
            onTertiary = OnTertiaryLight,
            tertiaryContainer = TertiaryContainerLight,
            onTertiaryContainer = OnTertiaryContainerLight,
            error = ErrorLight,
            onError = OnErrorLight,
            errorContainer = ErrorContainerLight,
            onErrorContainer = OnErrorContainerLight,
            background = BackgroundLight,
            onBackground = OnBackgroundLight,
            surface = SurfaceLight,
            onSurface = OnSurfaceLight,
            surfaceVariant = SurfaceVariantLight,
            onSurfaceVariant = OnSurfaceVariantLight,
            outline = OutlineLight,
            outlineVariant = OutlineVariantLight,
            scrim = ScrimLight,
            inverseSurface = InverseSurfaceLight,
            inverseOnSurface = InverseOnSurfaceLight,
            inversePrimary = InversePrimaryLight,
            surfaceDim = SurfaceDimLight,
            surfaceBright = SurfaceBrightLight,
            surfaceContainerLowest = SurfaceContainerLowestLight,
            surfaceContainerLow = SurfaceContainerLowLight,
            surfaceContainer = SurfaceContainerLight,
            surfaceContainerHigh = SurfaceContainerHighLight,
            surfaceContainerHighest = SurfaceContainerHighestLight,
        )

        private val DarkColorScheme = darkColorScheme(
            primary = PrimaryDark,
            onPrimary = OnPrimaryDark,
            primaryContainer = PrimaryContainerDark,
            onPrimaryContainer = OnPrimaryContainerDark,
            secondary = SecondaryDark,
            onSecondary = OnSecondaryDark,
            secondaryContainer = SecondaryContainerDark,
            onSecondaryContainer = OnSecondaryContainerDark,
            tertiary = TertiaryDark,
            onTertiary = OnTertiaryDark,
            tertiaryContainer = TertiaryContainerDark,
            onTertiaryContainer = OnTertiaryContainerDark,
            error = ErrorDark,
            onError = OnErrorDark,
            errorContainer = ErrorContainerDark,
            onErrorContainer = OnErrorContainerDark,
            background = BackgroundDark,
            onBackground = OnBackgroundDark,
            surface = SurfaceDark,
            onSurface = OnSurfaceDark,
            surfaceVariant = SurfaceVariantDark,
            onSurfaceVariant = OnSurfaceVariantDark,
            outline = OutlineDark,
            outlineVariant = OutlineVariantDark,
            scrim = ScrimDark,
            inverseSurface = InverseSurfaceDark,
            inverseOnSurface = InverseOnSurfaceDark,
            inversePrimary = InversePrimaryDark,
            surfaceDim = SurfaceDimDark,
            surfaceBright = SurfaceBrightDark,
            surfaceContainerLowest = SurfaceContainerLowestDark,
            surfaceContainerLow = SurfaceContainerLowDark,
            surfaceContainer = SurfaceContainerDark,
            surfaceContainerHigh = SurfaceContainerHighDark,
            surfaceContainerHighest = SurfaceContainerHighestDark,
        )

        internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

        @Composable
        internal fun AppTheme(
            content: @Composable () -> Unit
        ) {
            val systemIsDark = isSystemInDarkTheme()
            val isDarkState = remember(systemIsDark) { mutableStateOf(systemIsDark) }
            CompositionLocalProvider(
                LocalThemeIsDark provides isDarkState
            ) {
                val isDark by isDarkState
                SystemAppearance(!isDark)
                MaterialTheme(
                    colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
                    content = { Surface(content = content) }
                )
            }
        }

        @Composable
        internal expect fun SystemAppearance(isDark: Boolean)

    """.trimIndent()
}

class DesktopThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jvmMain/kotlin/${info.packagePath}/theme/Theme.jvm.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.runtime.Composable

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
        }
    """.trimIndent()
}

class WebThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/webMain/kotlin/${info.packagePath}/theme/Theme.web.kt"
    override val content = """
    package ${info.packageId}.theme

    import androidx.compose.runtime.Composable

    @Composable
    internal actual fun SystemAppearance(isDark: Boolean) {
    }
""".trimIndent()
}

class IosThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/iosMain/kotlin/${info.packagePath}/theme/Theme.ios.kt"
    override val content = """
        package ${info.packageId}.theme

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import platform.UIKit.UIApplication
        import platform.UIKit.UIStatusBarStyleDarkContent
        import platform.UIKit.UIStatusBarStyleLightContent
        import platform.UIKit.setStatusBarStyle

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
            LaunchedEffect(isDark) {
                UIApplication.sharedApplication.setStatusBarStyle(
                    if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
                )
            }
        }
    """.trimIndent()
}

class AndroidThemeKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/androidMain/kotlin/${info.packagePath}/theme/Theme.android.kt"
    override val content = """
        package ${info.packageId}.theme

        import android.app.Activity
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.platform.LocalView
        import androidx.core.view.WindowInsetsControllerCompat

        @Composable
        internal actual fun SystemAppearance(isDark: Boolean) {
            val view = LocalView.current
            LaunchedEffect(isDark) {
                val window = (view.context as Activity).window
                WindowInsetsControllerCompat(window, window.decorView).apply {
                    isAppearanceLightStatusBars = isDark
                    isAppearanceLightNavigationBars = isDark
                }
            }
        }
    """.trimIndent()
}