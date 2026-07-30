package wizard.icon

import wizard.AppIcon
import wizard.BinaryFile
import wizard.ProjectFile

actual fun createAndroidLauncherIcon(path: String, icon: AppIcon, size: Int): ProjectFile = getAndroidAppIcon(path)
actual fun createAndroidBackgroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile = getAndroidAppIcon(path)
actual fun createAndroidForegroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile = getAndroidAppIcon(path)
actual fun createAndroidMonochromeIcon(path: String, icon: AppIcon, size: Int): ProjectFile = getAndroidAppIcon(path)

private fun getAndroidAppIcon(name: String) = object : BinaryFile {
    override val path = "androidApp/src/main/res/$name"
    override val resourcePath = "android-app-icons/$name"
}