package wizard.files.kmpLibrary

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {
        appendLine("# ${info.name}")
        appendLine("")
        appendLine("Kotlin Multiplatform Library")
        appendLine("")
        appendLine("### Run Sample App")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine(" - Desktop JVM: `./gradlew :sample:composeApp:run`")
        }
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine(" - Android: `open project in Android Studio and run the sample app`")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine(" - iOS: `open 'sample/iosApp/iosApp.xcodeproj' in Xcode and run the sample app`")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine(" - JavaScript: `./gradlew :sample:composeApp:jsBrowserRun`")
        }
        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine(" - Wasm: `./gradlew :sample:composeApp:wasmJsBrowserRun`")
        }
        if (info.platforms.any { it in ProjectPlatform.binaryPlatforms }) {
            appendLine(" - Linux/Macos/Windows native: `./gradlew :sample:terminalApp:runDebugExecutable[architecture]`")
        }
        appendLine("")
        appendLine("### Publish to MavenLocal")
        appendLine("")
        appendLine("1) Run `./gradlew :${info.moduleName}:publishToMavenLocal`")
        appendLine("2) Open `~/.m2/repository/${info.packageId.replace(".", "/")}/`")
        appendLine("")
        appendLine("### Publish to MavenCentral")
        appendLine("")
        appendLine("1) Create an account and a namespace on Sonatype:  ")
        appendLine("   https://central.sonatype.org/register/central-portal/#create-an-account")
        appendLine("2) Add developer id, name, email and the project url to  ")
        appendLine("   `./${info.moduleName}/build.gradle.kts`")
        appendLine("3) Generate a GPG key:  ")
        appendLine("   https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/#generating-a-gpg-key-pair")
        appendLine("   ```")
        appendLine("   gpg --full-gen-key")
        appendLine("   gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXX")
        appendLine("   gpg --export-secret-key XXXXXXXX > XXXXXXXX.gpg")
        appendLine("   ```")
        appendLine("4) Add these lines to `gradle.properties`:")
        appendLine("   ```")
        appendLine("   signing.keyId=XXXXXXXX")
        appendLine("   signing.password=[key password]")
        appendLine("   signing.secretKeyRingFile=../XXXXXXXX.gpg")
        appendLine("   mavenCentralUsername=[generated username]")
        appendLine("   mavenCentralPassword=[generated password]")
        appendLine("   ```")
        appendLine("5) Run `./gradlew :${info.moduleName}:publishAndReleaseToMavenCentral --no-configuration-cache`")
    }
}