package wizard.files

import wizard.*

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {

        appendLine("# Compose Multiplatform Application")
        appendLine("")
        if (info.hasPlatform(ComposePlatform.Android) || info.hasPlatform(ComposePlatform.Ios)) {
            appendLine("## Before running!")
            if (info.hasPlatform(ComposePlatform.Ios)) {
                appendLine(" - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)")
            }
            if (info.hasPlatform(ComposePlatform.Android)) {
                appendLine(" - install JDK 17 on your machine")
                appendLine(" - add `local.properties` file to the project root and set a path to Android SDK there")
            }
            appendLine("")
        }

        if (info.hasPlatform(ComposePlatform.Android)) {
            appendLine("### Android")
            appendLine("To run the application on android device/emulator:  ")
            appendLine(" - open project in Android Studio and run imported android run configuration")
            appendLine("")
            appendLine("To build the application bundle:")
            appendLine(" - run `./gradlew :composeApp:assembleDebug`")
            appendLine(" - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`")
            appendLine("")
        }

        if (info.hasPlatform(ComposePlatform.Desktop)) {
            appendLine("### Desktop")
            appendLine("Run the desktop application: `./gradlew :composeApp:run`")
            appendLine("")
        }

        if (info.hasPlatform(ComposePlatform.Ios)) {
            appendLine("### iOS")
            appendLine("To run the application on iPhone device/simulator:")
            appendLine(" - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration")
            appendLine(" - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio")
            appendLine("")
        }

        if (info.hasPlatform(ComposePlatform.Browser)) {
            appendLine("### Browser")
            appendLine("Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun`")
            appendLine("")
        }
    }
}
