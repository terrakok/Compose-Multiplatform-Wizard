package wizard.files

import wizard.ProjectFile

class GradleProperties(enableExperimentalCompose: Boolean) : ProjectFile {
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
            kotlin.code.style=official
            kotlin.js.compiler=ir
            kotlin.daemon.jvmargs=-Xmx4G

            #Android
            android.useAndroidX=true
            android.nonTransitiveRClass=true
        """.trimIndent())
        if (enableExperimentalCompose) {
            appendLine("""
                
                #Compose
                org.jetbrains.compose.experimental.jscanvas.enabled=true
            """.trimIndent())
        }
    }
}