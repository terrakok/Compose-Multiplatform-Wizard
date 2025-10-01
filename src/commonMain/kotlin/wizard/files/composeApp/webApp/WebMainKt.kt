package wizard.files.composeApp.webApp

import wizard.ProjectFile
import wizard.ProjectInfo

class WebMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "webApp/src/commonMain/kotlin/main.kt"
    override val content = """
        import androidx.compose.ui.ExperimentalComposeUiApi
        import androidx.compose.ui.window.ComposeViewport
        import ${info.packageId}.App

        @OptIn(ExperimentalComposeUiApi::class)
        fun main() = ComposeViewport { App() }

    """.trimIndent()
}
