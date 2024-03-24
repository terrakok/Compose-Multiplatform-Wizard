package wizard.files.composeApp

import wizard.*

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {

        appendLine("# Compose Multiplatform Application")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Android) || info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("## Before running!")
            if (info.hasPlatform(ProjectPlatform.Ios)) {
                appendLine(" - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)")
            }
            if (info.hasPlatform(ProjectPlatform.Android)) {
                appendLine(" - install JDK 17 or higher on your machine")
                appendLine(" - add `local.properties` file to the project root and set a path to Android SDK there")
            }
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("### Android")
            appendLine("To run the application on android device/emulator:  ")
            appendLine(" - open project in Android Studio and run imported android run configuration")
            appendLine("")
            appendLine("To build the application bundle:")
            appendLine(" - run `./gradlew :${info.moduleName}:assembleDebug`")
            appendLine(" - find `.apk` file in `${info.moduleName}/build/outputs/apk/debug/${info.moduleName}-debug.apk`")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("### Desktop")
            appendLine("Run the desktop application: `./gradlew :${info.moduleName}:run`")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("### iOS")
            appendLine("To run the application on iPhone device/simulator:")
            appendLine(" - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration")
            appendLine(" - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("### Browser (JS)")
            appendLine("Run the browser application: `./gradlew :${info.moduleName}:jsBrowserDevelopmentRun --continue`")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("### Browser (Wasm)")
            appendLine("Run the browser application: `./gradlew :${info.moduleName}:wasmJsBrowserDevelopmentRun --continue`")
            appendLine("")
        }
    }
}