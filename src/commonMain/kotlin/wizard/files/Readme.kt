package wizard.files

import wizard.*

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {

        appendLine("# Compose Multiplatform Application")
        appendLine("")
        if (info.hasAndroid || info.hasIos) {
            appendLine("## Before running!")
            if (info.hasIos) {
                appendLine(" - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)")
            }
            if (info.hasAndroid) {
                appendLine(" - install JDK 8 on your machine")
                appendLine(" - add `local.properties` file to the project root and set a path to Android SDK there")
            }
            if (info.hasIos) {
                appendLine(" - run `./gradlew podInstall` in the project root")
            }
            appendLine("")
        }

        if (info.hasAndroid) {
            appendLine("### Android")
            appendLine("To run the application on android device/emulator:  ")
            appendLine(" - open project in Android Studio and run imported android run configuration")
            appendLine("")
            appendLine("To build the application bundle:")
            appendLine(" - run `./gradlew :composeApp:assembleDebug`")
            appendLine(" - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`")
            appendLine("")
        }

        if (info.hasDesktop) {
            appendLine("### Desktop")
            appendLine("Run the desktop application: `./gradlew :composeApp:run`")
            appendLine("")
        }

        if (info.hasIos) {
            appendLine("### iOS")
            appendLine("To run the application on iPhone device/simulator:")
            appendLine(" - Open `iosApp/iosApp.xcworkspace` in Xcode and run standard configuration")
            appendLine(" - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio")
            appendLine("")
        }

        if (info.hasBrowser) {
            appendLine("### Browser")
            appendLine("Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun`")
            appendLine("")
        }
    }
}