package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class AppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/App.kt"
    override val content = """
        package ${info.packageId}
        
        import androidx.compose.foundation.layout.Arrangement
        import androidx.compose.foundation.layout.Column
        import androidx.compose.foundation.layout.Row
        import androidx.compose.foundation.layout.Spacer
        import androidx.compose.foundation.layout.WindowInsets
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.foundation.layout.fillMaxWidth
        import androidx.compose.foundation.layout.padding
        import androidx.compose.foundation.layout.safeDrawing
        import androidx.compose.foundation.layout.size
        import androidx.compose.foundation.layout.windowInsetsPadding
        import androidx.compose.foundation.text.KeyboardOptions
        import androidx.compose.material.icons.Icons
        import androidx.compose.material.icons.filled.Close
        import androidx.compose.material.icons.filled.DarkMode
        import androidx.compose.material.icons.filled.Edit
        import androidx.compose.material.icons.filled.LightMode
        import androidx.compose.material3.Button
        import androidx.compose.material3.Icon
        import androidx.compose.material3.IconButton
        import androidx.compose.material3.MaterialTheme
        import androidx.compose.material3.OutlinedTextField
        import androidx.compose.material3.Text
        import androidx.compose.material3.TextButton
        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.getValue
        import androidx.compose.runtime.mutableStateOf
        import androidx.compose.runtime.remember
        import androidx.compose.runtime.setValue
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.text.input.KeyboardType
        import androidx.compose.ui.text.input.PasswordVisualTransformation
        import androidx.compose.ui.text.input.VisualTransformation
        import androidx.compose.ui.unit.dp
        import ${info.packageId}.theme.AppTheme
        import ${info.packageId}.theme.LocalThemeIsDark

        @Composable
        internal fun App() = AppTheme {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisibility by remember { mutableStateOf(false) }

            Column(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.weight(1.0f))

                    var isDark by LocalThemeIsDark.current
                    IconButton(
                        onClick = { isDark = !isDark }
                    ) {
                        Icon(
                            modifier = Modifier.padding(8.dp).size(20.dp),
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = null
                        )
                    }
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            val imageVector = if (passwordVisibility) Icons.Default.Close else Icons.Default.Edit
                            Icon(imageVector, contentDescription = if (passwordVisibility) "Hide password" else "Show password")
                        }
                    }
                )

                Button(
                    onClick = { /* Handle login logic here */ },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Login")
                }

                TextButton(
                    onClick = { openUrl("https://github.com/terrakok") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Open github")
                }
            }
        }
        
        internal expect fun openUrl(url: String?)
    """.trimIndent()
}

class AndroidAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/androidMain/kotlin/${info.packagePath}/App.android.kt"
    override val content = """
        package ${info.packageId}

        import android.app.Application
        import android.content.Intent
        import android.net.Uri
        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        
        class AndroidApp : Application() {
            companion object {
                lateinit var INSTANCE: AndroidApp
            }

            override fun onCreate() {
                super.onCreate()
                INSTANCE = this
            }
        }
        
        class AppActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContent {
                    App()
                }
            }
        }
        
        internal actual fun openUrl(url: String?) {
            val uri = url?.let { Uri.parse(it) } ?: return
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = uri
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            AndroidApp.INSTANCE.startActivity(intent)
        }
    """.trimIndent()
}

class DesktopAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/desktopMain/kotlin/${info.packagePath}/App.desktop.kt"
    override val content = """
        package ${info.packageId}

        import java.awt.Desktop
        import java.net.URI

        internal actual fun openUrl(url: String?) {
            val uri = url?.let { URI.create(it) } ?: return
            Desktop.getDesktop().browse(uri)
        }
    """.trimIndent()
}

class IosAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/iosMain/kotlin/${info.packagePath}/App.ios.kt"
    override val content = """
        package ${info.packageId}

        import platform.Foundation.NSURL
        import platform.UIKit.UIApplication

        internal actual fun openUrl(url: String?) {
            val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    """.trimIndent()
}

class BrowserAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/jsMain/kotlin/${info.packagePath}/App.js.kt"
    override val content = """
        package ${info.packageId}

        import kotlinx.browser.window

        internal actual fun openUrl(url: String?) {
            url?.let { window.open(it) }
        }
    """.trimIndent()
}