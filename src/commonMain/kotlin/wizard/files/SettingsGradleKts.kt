package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo

class SettingsGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "settings.gradle.kts"
    override val content = """
rootProject.name = "${info.name}"
include(":composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        val kotlin = "${info.kotlinVersion}"
        kotlin("android").version(kotlin)
        kotlin("multiplatform").version(kotlin)
        kotlin("native.cocoapods").version(kotlin)

        val agp = "${info.agpVersion}"
        id("com.android.application").version(agp)

        id("org.jetbrains.compose").version("${info.composeVersion}")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
"""
}