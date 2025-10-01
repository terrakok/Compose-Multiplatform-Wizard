package wizard.files.composeApp.desktop

import wizard.ProjectFile
import wizard.ProjectInfo

class DesktopMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "desktopApp/src/main/kotlin/main.kt"
    override val content = buildString {
        appendLine(
            """
                |import androidx.compose.ui.unit.dp
                |import androidx.compose.ui.window.Window
                |import androidx.compose.ui.window.application
                |import androidx.compose.ui.window.rememberWindowState
                |import java.awt.Dimension
                |import ${info.packageId}.App
                |
                |fun main() = application {
                |    Window(
                |        title = "${info.name}",
                |        state = rememberWindowState(width = 800.dp, height = 600.dp),
                |        onCloseRequest = ::exitApplication,
                |    ) {
                |        window.minimumSize = Dimension(350, 600)
                |        App()
                |    }
                |}
                |
            """.trimMargin()
        )
    }
}
