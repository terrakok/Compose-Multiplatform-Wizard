package wizard.files

import wizard.ProjectFile

class GradleProperties(enableExperimentalCompose: Boolean,useRoom:Boolean = false) : ProjectFile {
    override val path = "gradle.properties"
    override val content = buildString {
        appendLine("""
            #Gradle
            org.gradle.jvmargs=-Xmx4G -Dfile.encoding=UTF-8 -Dkotlin.daemon.jvm.options\="-Xmx4G"
            org.gradle.caching=true
            org.gradle.configuration-cache=true
            org.gradle.daemon=true
            org.gradle.parallel=true

            #Kotlin
            kotlin.code.style=official
            kotlin.js.compiler=ir

            #Android
            android.useAndroidX=true
            android.nonTransitiveRClass=true
        """.trimIndent())
        if (enableExperimentalCompose) {
            appendLine("""
                
                #Compose
                org.jetbrains.compose.experimental.uikit.enabled=true
                org.jetbrains.compose.experimental.jscanvas.enabled=true
                org.jetbrains.compose.experimental.wasm.enabled=true
            """.trimIndent())
        }
        if (useRoom) {
            appendLine("""
                
                #As of Kotlin 1.9.20, you must add the property kotlin.native.disableCompilerDaemon = true to the gradle.properties configuration file for Room's KSP processor to function properly.
                #For more information, see https://youtrack.jetbrains.com/issue/KT-65761.
                kotlin.native.disableCompilerDaemon = true
            """.trimIndent())
        }
    }
}