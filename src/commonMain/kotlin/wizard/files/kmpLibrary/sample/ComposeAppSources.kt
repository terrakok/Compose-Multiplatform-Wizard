package wizard.files.kmpLibrary.sample

import wizard.ProjectFile
import wizard.ProjectInfo

class ComposeAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "sample/composeApp/src/commonMain/kotlin/sample/app/App.kt"
    override val content = """
        package sample.app

        import androidx.compose.foundation.background
        import androidx.compose.foundation.layout.Box
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.foundation.text.BasicText
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.graphics.Color
        import ${info.packageId}.getFibonacciNumbers

        @Composable
        fun App() {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                BasicText("getFibonacciNumbers(7)=${'$'}{getFibonacciNumbers(7).joinToString(", ")}")
            }
        }
    """.trimIndent()
}

class AndroidMainKt() : ProjectFile {
    override val path = "sample/composeApp/src/androidMain/kotlin/sample/app/main.kt"
    override val content = """
        package sample.app

        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.activity.enableEdgeToEdge

        class AppActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                enableEdgeToEdge()
                setContent { App() }
            }
        }
    """.trimIndent()
}

class AndroidManifestXml() : ProjectFile {
    override val path = "sample/composeApp/src/androidMain/AndroidManifest.xml"
    override val content = """
        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android">

            <application
                    android:icon="@android:mipmap/sym_def_app_icon"
                    android:label="sample"
                    android:theme="@android:style/Theme.Material.NoActionBar">
                <activity
                    android:name=".AppActivity"
                    android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"
                    android:launchMode="singleInstance"
                    android:windowSoftInputMode="adjustPan"
                    android:exported="true">
                    <intent-filter>
                        <action android:name="android.intent.action.MAIN" />
                        <category android:name="android.intent.category.LAUNCHER" />
                    </intent-filter>
                </activity>
            </application>

        </manifest>
    """.trimIndent()
}

class IosMainKt() : ProjectFile {
    override val path = "sample/composeApp/src/iosMain/kotlin/sample/app/main.kt"
    override val content = """
        import androidx.compose.ui.window.ComposeUIViewController
        import platform.UIKit.UIViewController
        import sample.app.App

        fun MainViewController(): UIViewController = ComposeUIViewController { App() }
    """.trimIndent()
}

class JvmMainKt() : ProjectFile {
    override val path = "sample/composeApp/src/jvmMain/kotlin/sample/app/main.kt"
    override val content = """
        import androidx.compose.ui.unit.dp
        import androidx.compose.ui.window.Window
        import androidx.compose.ui.window.application
        import androidx.compose.ui.window.rememberWindowState
        import sample.app.App
        import java.awt.Dimension

        fun main() = application {
            Window(
                title = "sample",
                state = rememberWindowState(width = 800.dp, height = 600.dp),
                onCloseRequest = ::exitApplication,
            ) {
                window.minimumSize = Dimension(350, 600)
                App()
            }
        }
    """.trimIndent()
}

class WebMainKt(wasm: Boolean) : ProjectFile {
    override val path = "sample/composeApp/src/${if (wasm) "wasmJs" else "js" }Main/kotlin/sample/app/main.kt"
    override val content = """
        import androidx.compose.ui.ExperimentalComposeUiApi
        import androidx.compose.ui.window.ComposeViewport
        import kotlinx.browser.document
        import sample.app.App

        @OptIn(ExperimentalComposeUiApi::class)
        fun main() {
            val body = document.body ?: return
            ComposeViewport(body) {
                App()
            }
        }
    """.trimIndent()
}

class JsIndexHtml() : ProjectFile {
    override val path = "sample/composeApp/src/jsMain/resources/index.html"
    override val content = """
        <!doctype html>
        <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>sample</title>
                <script src="skiko.js"></script>
                <style>
                    html,
                    body {
                        width: 100%;
                        height: 100%;
                        margin: 0;
                        padding: 0;
                        overflow: hidden;
                    }
                </style>
            </head>
            <body></body>
            <script src="sampleApp.js"></script>
        </html>
    """.trimIndent()
}

class WasmIndexHtml() : ProjectFile {
    override val path = "sample/composeApp/src/wasmJsMain/resources/index.html"
    override val content = """
        <!doctype html>
        <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>sample</title>
                <style>
                    html,
                    body {
                        width: 100%;
                        height: 100%;
                        margin: 0;
                        padding: 0;
                        overflow: hidden;
                    }
                </style>
            </head>
            <body></body>
            <script src="sampleApp.js"></script>
        </html>
    """.trimIndent()
}