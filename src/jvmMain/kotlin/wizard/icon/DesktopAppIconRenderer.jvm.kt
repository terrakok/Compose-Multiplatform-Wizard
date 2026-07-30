package wizard.icon

import wizard.AppIcon
import wizard.BinaryFile
import wizard.ProjectFile

actual fun createDesktopIcon(icon: AppIcon, type: Desktop): ProjectFile {
    val resourcePath = when (type) {
        Desktop.Linux -> "desktop-app-icons/LinuxIcon.png"
        Desktop.Windows -> "desktop-app-icons/WindowsIcon.ico"
        Desktop.Macos -> "desktop-app-icons/MacosIcon.icns"
    }
    return object : BinaryFile {
        override val path = type.path
        override val resourcePath = resourcePath
    }
}