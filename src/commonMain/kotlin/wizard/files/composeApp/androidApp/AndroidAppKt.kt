package wizard.files.composeApp.androidApp

import wizard.*

class AndroidAppActivityKt(info: ProjectInfo) : ProjectFile {
    override val path = "androidApp/src/main/kotlin/${info.packagePath}/AppActivity.kt"
    override val content = """
        package ${info.packageId}

        import android.app.Activity
        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.activity.enableEdgeToEdge
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.LaunchedEffect
        import androidx.compose.ui.platform.LocalView
        import androidx.core.view.WindowInsetsControllerCompat
        
        class AppActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContent { 
                    App(onThemeChanged = { ThemeChanged(it) }) 
                }
            }
        }

        @Composable
        private fun ThemeChanged(isDark: Boolean) {
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
