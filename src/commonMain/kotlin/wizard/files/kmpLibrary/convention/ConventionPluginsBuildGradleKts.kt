package wizard.files.kmpLibrary.convention

import wizard.ProjectFile
import wizard.ProjectInfo

class ConventionPluginsBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "convention-plugins/build.gradle.kts"
    override val content = """
plugins {
    `kotlin-dsl` // Is needed to turn our build logic written in Kotlin into Gralde Plugin
}

repositories {
    gradlePluginPortal() // To use 'maven-publish' and 'signing' plugins in our own plugin
}
    """.trimIndent()
}