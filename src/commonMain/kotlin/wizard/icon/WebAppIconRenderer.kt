package wizard.icon

import wizard.AppIcon
import wizard.AppIconRenderer
import wizard.ProjectFile

data class WebIconSpec(
    val filename: String,
    val size: Int
) {
    val isPng = filename.endsWith(".png")

    companion object {
        val icoSizes = listOf(16, 32, 48, 64, 128, 256)
    }
}

private val iconSpecs = listOf(
    WebIconSpec("android-chrome-192x192.png", 192),
    WebIconSpec("android-chrome-512x512.png", 512),
    WebIconSpec("apple-touch-icon.png", 180),
    WebIconSpec("favicon.ico", -1),
    WebIconSpec("favicon-16x16.png", 16),
    WebIconSpec("favicon-32x32.png", 32),
)

object WebAppIconRenderer : AppIconRenderer {
    override fun render(icon: AppIcon): List<ProjectFile> {
        return iconSpecs.map { spec -> createWebIcon(spec, icon) }
    }
}

expect fun createWebIcon(spec: WebIconSpec,  icon: AppIcon): ProjectFile