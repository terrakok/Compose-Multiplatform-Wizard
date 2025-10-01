package wizard.files.composeApp.webApp

import wizard.GradleModule
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.isPlugin
import wizard.libraryNotation
import wizard.pluginNotation

class WebBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "webApp/build.gradle.kts"
    override val content = buildString {
        val plugins = info.dependencies.filter { it.modules.contains(GradleModule.WEB) && it.isPlugin() }
        val libs = info.dependencies.filter { it.modules.contains(GradleModule.WEB) && !it.isPlugin() }
        appendLine("plugins {")
        plugins.forEach { dep -> appendLine("    ${dep.pluginNotation}") }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        appendLine("    js {")
        appendLine("        browser()")
        appendLine("        binaries.executable()")
        appendLine("    }")
        appendLine("")
        appendLine("    wasmJs {")
        appendLine("        browser()")
        appendLine("        binaries.executable()")
        appendLine("    }")
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(project(\":${info.moduleName}\"))")
        appendLine("            implementation(compose.ui)")
        libs.forEach { dep -> appendLine("    ${dep.libraryNotation}") }
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
    }
}
