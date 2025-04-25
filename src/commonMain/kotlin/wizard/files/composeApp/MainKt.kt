package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.enableJvmHotReload

class DesktopMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jvmMain/kotlin/main.kt"
    override val content = buildString {
        appendLine(
            """
                |import androidx.compose.desktop.ui.tooling.preview.Preview
                |import androidx.compose.runtime.Composable
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
                |@Preview
                |@Composable
                |fun AppPreview() { App() }
            """.trimMargin()
        )

    }
}

class IosMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/iosMain/kotlin/main.kt"
    override val content = """
        import androidx.compose.ui.window.ComposeUIViewController
        import ${info.packageId}.App
        import platform.UIKit.UIViewController

        fun MainViewController(): UIViewController = ComposeUIViewController { App() }
        
    """.trimIndent()
}

class JsMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jsMain/kotlin/main.kt"
    override val content = """
        import androidx.compose.ui.ExperimentalComposeUiApi
        import androidx.compose.ui.window.ComposeViewport
        import ${info.packageId}.App
        import org.jetbrains.skiko.wasm.onWasmReady
        import kotlinx.browser.document

        @OptIn(ExperimentalComposeUiApi::class)
        fun main() {
            onWasmReady {
                val body = document.body ?: return@onWasmReady
                ComposeViewport(body) {
                    App()
                }
            }
        }

    """.trimIndent()
}

class WasmJsMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/wasmJsMain/kotlin/main.kt"
    override val content = """
        import androidx.compose.ui.ExperimentalComposeUiApi
        import androidx.compose.ui.window.ComposeViewport
        import ${info.packageId}.App
        import kotlinx.browser.document

        @OptIn(ExperimentalComposeUiApi::class)
        fun main() {
            val body = document.body ?: return
            ComposeViewport(body) {
                App()
            }
        }

    """.trimIndent()
}