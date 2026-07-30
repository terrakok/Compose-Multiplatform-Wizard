package wizard.icon

import wizard.*

actual fun createDesktopIcon(icon: AppIcon, type: Desktop): ProjectFile = object : RawFile {
    override val path = type.path
    override val arrayBuffer
        get() = when (type) {
            Desktop.Linux -> icon.toPng(512)
            Desktop.Windows -> icon.toIco(Desktop.windowsIcoSizes)
            Desktop.Macos -> icon.copy(cornerRadiusPercent = 0).toIcns(Desktop.macosIcnsSizes)
        }
}
