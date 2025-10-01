package wizard.files.composeApp.androidApp

import wizard.BinaryFile
import wizard.ProjectFile
import wizard.ProjectInfo

fun AndroidAppIcons(info: ProjectInfo): List<ProjectFile> = listOf(
    getAndroidAppIcon(info.moduleName, "mipmap-anydpi-v26/ic_launcher.xml"),
    getAndroidAppIcon(info.moduleName, "mipmap-hdpi/ic_launcher.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-hdpi/ic_launcher_background.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-hdpi/ic_launcher_foreground.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-hdpi/ic_launcher_monochrome.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-mdpi/ic_launcher.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-mdpi/ic_launcher_background.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-mdpi/ic_launcher_foreground.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-mdpi/ic_launcher_monochrome.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xhdpi/ic_launcher.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xhdpi/ic_launcher_background.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xhdpi/ic_launcher_foreground.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xhdpi/ic_launcher_monochrome.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxhdpi/ic_launcher.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxhdpi/ic_launcher_background.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxhdpi/ic_launcher_foreground.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxhdpi/ic_launcher_monochrome.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxxhdpi/ic_launcher.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxxhdpi/ic_launcher_background.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxxhdpi/ic_launcher_foreground.png"),
    getAndroidAppIcon(info.moduleName, "mipmap-xxxhdpi/ic_launcher_monochrome.png"),
)

private fun getAndroidAppIcon(moduleName: String, name: String) = object : BinaryFile {
    override val path = "androidApp/src/main/res/$name"
    override val resourcePath = "android-app-icons/$name"
}