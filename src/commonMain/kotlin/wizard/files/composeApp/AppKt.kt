package wizard.files.composeApp

import wizard.*

class AppKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/kotlin/${info.packagePath}/App.kt"
    override val content = """
        package ${info.packageId}
        
        import androidx.compose.animation.core.*
        import androidx.compose.foundation.Image
        import androidx.compose.foundation.layout.*
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.draw.rotate
        import androidx.compose.ui.graphics.ColorFilter
        import androidx.compose.ui.text.font.FontFamily
        import androidx.compose.ui.unit.dp
        import ${info.getResourcesPackage()}.*
        import ${info.packageId}.theme.AppTheme
        import ${info.packageId}.theme.LocalThemeIsDark
        import org.jetbrains.compose.resources.Font
        import org.jetbrains.compose.resources.stringResource
        import org.jetbrains.compose.resources.vectorResource

        @Composable
        internal fun App() = AppTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.cyclone),
                    fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
                    style = MaterialTheme.typography.displayLarge
                )

                var isAnimate by remember { mutableStateOf(false) }
                val transition = rememberInfiniteTransition()
                val rotate by transition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing)
                    )
                )

                Image(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(16.dp)
                        .run { if (isAnimate) rotate(rotate) else this },
                    imageVector = vectorResource(Res.drawable.ic_cyclone),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                    contentDescription = null
                )

                ElevatedButton(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .widthIn(min = 200.dp),
                    onClick = { isAnimate = !isAnimate },
                    content = {
                        Icon(vectorResource(Res.drawable.ic_rotate_right), contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(if (isAnimate) Res.string.stop else Res.string.run)
                        )
                    }
                )

                var isDark by LocalThemeIsDark.current
                val icon = remember(isDark) {
                    if (isDark) Res.drawable.ic_light_mode
                    else Res.drawable.ic_dark_mode
                }

                ElevatedButton(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
                    onClick = { isDark = !isDark },
                    content = {
                        Icon(vectorResource(icon), contentDescription = null)
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(Res.string.theme))
                    }
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
                    onClick = { openUrl("https://github.com/terrakok") },
                ) {
                    Text(stringResource(Res.string.open_github))
                }
            }
        }

        internal expect fun openUrl(url: String?)
    """.trimIndent()
}

class AndroidAppKt(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/androidMain/kotlin/${info.packagePath}/App.android.kt"
    override val content = """
        package ${info.packageId}

        import android.app.Application
        import android.content.Intent
        import android.net.Uri
        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.activity.enableEdgeToEdge
        
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
                enableEdgeToEdge()
                setContent { App() }
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
    override val path = "${info.moduleName}/src/jvmMain/kotlin/${info.packagePath}/App.jvm.kt"
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
    override val path = "${info.moduleName}/src/iosMain/kotlin/${info.packagePath}/App.ios.kt"
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
    override val path = "${info.moduleName}/src/jsMain/kotlin/${info.packagePath}/App.js.kt"
    override val content = """
        package ${info.packageId}

        import kotlinx.browser.window

        internal actual fun openUrl(url: String?) {
            url?.let { window.open(it) }
        }
    """.trimIndent()
}