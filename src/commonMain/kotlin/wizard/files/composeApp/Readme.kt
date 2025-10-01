package wizard.files.composeApp

import wizard.*

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {

        appendLine("# ${info.name}")
        appendLine("")

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("### Android")
            appendLine("To run the application on android device/emulator:  ")
            appendLine(" - open project in Android Studio and run imported android run configuration  ")
            appendLine("")
            appendLine("To build the application bundle:  ")
            appendLine(" - run `./gradlew :androidApp:assembleDebug`  ")
            appendLine(" - find `.apk` file in `androidApp/build/outputs/apk/debug/androidApp-debug.apk`  ")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("### Desktop")
            appendLine("Run the desktop application: `./gradlew :desktopApp:run`  ")
            if (info.enableJvmHotReload) {
                appendLine("Run the desktop **hot reload** application: `./gradlew :desktopApp:hotRun --auto`  ")
            }
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("### iOS")
            appendLine("To run the application on iPhone device/simulator:  ")
            appendLine(" - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration  ")
            appendLine(" - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio  ")
            appendLine("")
        }

        if (info.hasWebPlatform()) {
            appendLine("### Web Distribution")
            appendLine("Build web distribution: `./gradlew :webApp:composeCompatibilityBrowserDistribution`  ")
            appendLine("Deploy a dir `webApp/build/dist/composeWebCompatibility/productionExecutable` to a web server  ")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("### JS Browser")
            appendLine("Run the browser application: `./gradlew :webApp:jsBrowserDevelopmentRun`  ")
            appendLine("")
        }

        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("### Wasm Browser")
            appendLine("Run the browser application: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`  ")
            appendLine("")
        }
    }
}