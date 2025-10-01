package wizard.files.composeApp.desktop

import wizard.BinaryFile
import wizard.ProjectInfo

fun DesktopAppIcons(info: ProjectInfo): List<BinaryFile> = listOf(
    object : BinaryFile {
        override val path = "desktopApp/appIcons/LinuxIcon.png"
        override val resourcePath = "desktop-app-icons/LinuxIcon.png"
    },
    object : BinaryFile {
        override val path = "desktopApp/appIcons/WindowsIcon.ico"
        override val resourcePath = "desktop-app-icons/WindowsIcon.ico"
    },
    object : BinaryFile {
        override val path = "desktopApp/appIcons/MacosIcon.icns"
        override val resourcePath = "desktop-app-icons/MacosIcon.icns"
    }
)
