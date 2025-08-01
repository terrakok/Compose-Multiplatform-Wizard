package wizard.files.composeApp

import wizard.BinaryFile
import wizard.ProjectFile
import wizard.ProjectInfo

fun DesktopAppIcons(info: ProjectInfo): List<BinaryFile> = listOf(
    object : BinaryFile {
        override val path = "${info.moduleName}/desktopAppIcons/LinuxIcon.png"
        override val resourcePath = "desktop-app-icons/LinuxIcon.png"
    },
    object : BinaryFile {
        override val path = "${info.moduleName}/desktopAppIcons/WindowsIcon.ico"
        override val resourcePath = "desktop-app-icons/WindowsIcon.ico"
    },
    object : BinaryFile {
        override val path = "${info.moduleName}/desktopAppIcons/MacosIcon.icns"
        override val resourcePath = "desktop-app-icons/MacosIcon.icns"
    }
)

fun WebFavIcons(info: ProjectInfo): List<BinaryFile> = listOf(
    getWebFavicon(info, "android-chrome-192x192.png"),
    getWebFavicon(info, "android-chrome-512x512.png"),
    getWebFavicon(info, "apple-touch-icon.png"),
    getWebFavicon(info, "favicon.ico"),
    getWebFavicon(info, "favicon-16x16.png"),
    getWebFavicon(info, "favicon-32x32.png"),
)

private fun getWebFavicon(info: ProjectInfo, fileName: String) = object : BinaryFile {
    override val path = "${info.moduleName}/src/webMain/resources/$fileName"
    override val resourcePath = "web-app-icons/$fileName"
}

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
    override val path = "$moduleName/src/androidMain/res/$name"
    override val resourcePath = "android-app-icons/$name"
}

fun IosAppIcons(): List<ProjectFile> = listOf(
    getIosAppIcon("AppIcon@2x.png"),
    getIosAppIcon("AppIcon@2x~ipad.png"),
    getIosAppIcon("AppIcon@3x.png"),
    getIosAppIcon("AppIcon-20@2x.png"),
    getIosAppIcon("AppIcon-20@2x~ipad.png"),
    getIosAppIcon("AppIcon-20@3x.png"),
    getIosAppIcon("AppIcon-20~ipad.png"),
    getIosAppIcon("AppIcon-29.png"),
    getIosAppIcon("AppIcon-29@2x.png"),
    getIosAppIcon("AppIcon-29@2x~ipad.png"),
    getIosAppIcon("AppIcon-29@3x.png"),
    getIosAppIcon("AppIcon-29~ipad.png"),
    getIosAppIcon("AppIcon-40@2x.png"),
    getIosAppIcon("AppIcon-40@2x~ipad.png"),
    getIosAppIcon("AppIcon-40@3x.png"),
    getIosAppIcon("AppIcon-40~ipad.png"),
    getIosAppIcon("AppIcon-60@2x~car.png"),
    getIosAppIcon("AppIcon-60@3x~car.png"),
    getIosAppIcon("AppIcon-83.5@2x~ipad.png"),
    getIosAppIcon("AppIcon~ios-marketing.png"),
    getIosAppIcon("AppIcon~ipad.png"),
    object : ProjectFile {
        override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json"
        override val content = """
        {
          "images": [
            {
              "filename": "AppIcon@2x.png",
              "idiom": "iphone",
              "scale": "2x",
              "size": "60x60"
            },
            {
              "filename": "AppIcon@3x.png",
              "idiom": "iphone",
              "scale": "3x",
              "size": "60x60"
            },
            {
              "filename": "AppIcon~ipad.png",
              "idiom": "ipad",
              "scale": "1x",
              "size": "76x76"
            },
            {
              "filename": "AppIcon@2x~ipad.png",
              "idiom": "ipad",
              "scale": "2x",
              "size": "76x76"
            },
            {
              "filename": "AppIcon-83.5@2x~ipad.png",
              "idiom": "ipad",
              "scale": "2x",
              "size": "83.5x83.5"
            },
            {
              "filename": "AppIcon-40@2x.png",
              "idiom": "iphone",
              "scale": "2x",
              "size": "40x40"
            },
            {
              "filename": "AppIcon-40@3x.png",
              "idiom": "iphone",
              "scale": "3x",
              "size": "40x40"
            },
            {
              "filename": "AppIcon-40~ipad.png",
              "idiom": "ipad",
              "scale": "1x",
              "size": "40x40"
            },
            {
              "filename": "AppIcon-40@2x~ipad.png",
              "idiom": "ipad",
              "scale": "2x",
              "size": "40x40"
            },
            {
              "filename": "AppIcon-20@2x.png",
              "idiom": "iphone",
              "scale": "2x",
              "size": "20x20"
            },
            {
              "filename": "AppIcon-20@3x.png",
              "idiom": "iphone",
              "scale": "3x",
              "size": "20x20"
            },
            {
              "filename": "AppIcon-20~ipad.png",
              "idiom": "ipad",
              "scale": "1x",
              "size": "20x20"
            },
            {
              "filename": "AppIcon-20@2x~ipad.png",
              "idiom": "ipad",
              "scale": "2x",
              "size": "20x20"
            },
            {
              "filename": "AppIcon-29.png",
              "idiom": "iphone",
              "scale": "1x",
              "size": "29x29"
            },
            {
              "filename": "AppIcon-29@2x.png",
              "idiom": "iphone",
              "scale": "2x",
              "size": "29x29"
            },
            {
              "filename": "AppIcon-29@3x.png",
              "idiom": "iphone",
              "scale": "3x",
              "size": "29x29"
            },
            {
              "filename": "AppIcon-29~ipad.png",
              "idiom": "ipad",
              "scale": "1x",
              "size": "29x29"
            },
            {
              "filename": "AppIcon-29@2x~ipad.png",
              "idiom": "ipad",
              "scale": "2x",
              "size": "29x29"
            },
            {
              "filename": "AppIcon-60@2x~car.png",
              "idiom": "car",
              "scale": "2x",
              "size": "60x60"
            },
            {
              "filename": "AppIcon-60@3x~car.png",
              "idiom": "car",
              "scale": "3x",
              "size": "60x60"
            },
            {
              "filename": "AppIcon~ios-marketing.png",
              "idiom": "ios-marketing",
              "scale": "1x",
              "size": "1024x1024"
            }
          ]
        }
        
        """.trimIndent()
    }
)

private fun getIosAppIcon(name: String) = object : BinaryFile {
    override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/$name"
    override val resourcePath = "ios-app-icons/$name"
}