package wizard.icon

import wizard.AppIcon
import wizard.AppIconRenderer
import wizard.ProjectFile

enum class Desktop(val path: String) {
    Linux("desktopApp/appIcons/LinuxIcon.png"),
    Windows("desktopApp/appIcons/WindowsIcon.ico"),
    Macos("desktopApp/appIcons/MacosIcon.icns");

    companion object {
        val windowsIcoSizes = listOf(16, 24, 32, 48, 64, 128, 256)
        val macosIcnsSizes = listOf(16, 32, 64, 128, 256, 512, 1024)
    }
}

object DesktopAppIconRenderer : AppIconRenderer {
    override fun render(icon: AppIcon): List<ProjectFile> {
        return Desktop.entries.map { createDesktopIcon(icon, it) }
    }
}

expect fun createDesktopIcon(icon: AppIcon, type: Desktop): ProjectFile