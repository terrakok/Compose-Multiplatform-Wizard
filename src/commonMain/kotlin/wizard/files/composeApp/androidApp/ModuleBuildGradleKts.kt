package wizard.files.composeApp.androidApp

import wizard.GradleModule
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.isPlugin
import wizard.libraryNotation
import wizard.pluginNotation

class AndroidAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "androidApp/build.gradle.kts"
    override val content = buildString {
        val plugins = info.dependencies.filter { it.modules.contains(GradleModule.ANDROID) && it.isPlugin() }
        val libs = info.dependencies.filter { it.modules.contains(GradleModule.ANDROID) && !it.isPlugin() }
        appendLine("plugins {")
        plugins.forEach { dep -> appendLine("    ${dep.pluginNotation}") }
        appendLine("}")
        appendLine("")
        appendLine("android {")
        appendLine("    namespace = \"${info.packageId}\"")
        appendLine("    compileSdk = ${info.androidTargetSdk}")
        appendLine("")
        appendLine("    defaultConfig {")
        appendLine("        minSdk = ${info.androidMinSdk}")
        appendLine("        targetSdk = ${info.androidTargetSdk}")
        appendLine("")
        appendLine("        applicationId = \"${info.packageId}.androidApp\"")
        appendLine("        versionCode = 1")
        appendLine("        versionName = \"1.0.0\"")
        appendLine("    }")
        appendLine("}")
        appendLine("")


        appendLine("kotlin {")
        appendLine("    jvmToolchain(17)")
        appendLine("}")
        appendLine("")

        appendLine("dependencies {")
        appendLine("    implementation(project(\":${info.moduleName}\"))")
        libs.forEach { dep -> appendLine("    ${dep.libraryNotation}") }
        appendLine("}")
    }
}
