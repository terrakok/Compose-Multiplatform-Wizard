package wizard.icon

import wizard.AppIcon
import wizard.AppIconRenderer
import wizard.ProjectFile

data class AndroidIconSpec(
    val sizeName: String,
    val size: Int,
    val layerSize: Int,
)

private val specs = listOf(
    AndroidIconSpec("anydpi-v26", -1, -1),
    AndroidIconSpec("hdpi", 72, 162),
    AndroidIconSpec("mdpi", 48, 108),
    AndroidIconSpec("xhdpi", 96, 216),
    AndroidIconSpec("xxhdpi", 144, 324),
    AndroidIconSpec("xxxhdpi", 192, 432),
)

object AndroidAppIconRenderer : AppIconRenderer {
    override fun render(icon: AppIcon): List<ProjectFile> {
        return specs.flatMap { spec ->
            if (spec.size == -1) {
                listOf(createAndroidXmlIcon("mipmap-${spec.sizeName}/ic_launcher.xml"))
            } else {
                listOf(
                    createAndroidLauncherIcon("mipmap-${spec.sizeName}/ic_launcher.png", icon, spec.size),
                    createAndroidBackgroundIcon("mipmap-${spec.sizeName}/ic_launcher_background.png", icon, spec.layerSize),
                    createAndroidForegroundIcon("mipmap-${spec.sizeName}/ic_launcher_foreground.png", icon, spec.layerSize),
                    createAndroidMonochromeIcon("mipmap-${spec.sizeName}/ic_launcher_monochrome.png", icon, spec.layerSize)
                )
            }
        }
    }
}

private fun createAndroidXmlIcon(path: String): ProjectFile = object : ProjectFile {
    override val path = "androidApp/src/main/res/$path"
    override val content: String = """
        <?xml version="1.0" encoding="utf-8"?>
        <adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
          <background android:drawable="@mipmap/ic_launcher_background"/>
          <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
          <monochrome android:drawable="@mipmap/ic_launcher_monochrome"/>
        </adaptive-icon>
    """.trimIndent()
}
expect fun createAndroidLauncherIcon(path: String, icon: AppIcon, size: Int): ProjectFile
expect fun createAndroidBackgroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile
expect fun createAndroidForegroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile
expect fun createAndroidMonochromeIcon(path: String, icon: AppIcon, size: Int): ProjectFile