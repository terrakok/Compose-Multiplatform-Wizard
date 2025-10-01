package wizard.files.composeApp.shared

import wizard.ProjectFile
import wizard.ProjectInfo

class IosMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/iosMain/kotlin/main.kt"
    override val content = """
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.window.ComposeUIViewController
        import ${info.packageId}.App
        import platform.UIKit.UIApplication
        import platform.UIKit.UIStatusBarStyleDarkContent
        import platform.UIKit.UIStatusBarStyleLightContent
        import platform.UIKit.UIViewController
        import platform.UIKit.setStatusBarStyle

        fun MainViewController(): UIViewController = ComposeUIViewController { 
            App(onThemeChanged = { ThemeChanged(it) })
        }
        
        @Composable
        private fun ThemeChanged(isDark: Boolean) {
            LaunchedEffect(isDark) {
                UIApplication.sharedApplication.setStatusBarStyle(
                    if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
                )
            }
        }
    """.trimIndent()
}
