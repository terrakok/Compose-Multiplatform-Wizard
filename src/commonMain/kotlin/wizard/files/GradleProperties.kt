package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

class GradleProperties(info: ProjectInfo) : ProjectFile {
    override val path = "gradle.properties"
    override val content = buildString {
        appendLine("""
            #Gradle
            org.gradle.jvmargs=-Xmx4G
            org.gradle.caching=true
            org.gradle.configuration-cache=true
            org.gradle.daemon=true
            org.gradle.parallel=true

            #Kotlin
            kotlin.daemon.jvmargs=-Xmx4G
            kotlin.native.binary.gc=cms
            kotlin.incremental.wasm=true

            #Android
            android.useAndroidX=true
        """.trimIndent())
        if (info.hasPlatform(ProjectPlatform.Js) || info.hasPlatform(ProjectPlatform.Macos)) {
            appendLine("")
            appendLine("#Compose")
            if (info.hasPlatform(ProjectPlatform.Macos)) {
                appendLine("org.jetbrains.compose.experimental.macos.enabled=true")
            }
        }
    }
}