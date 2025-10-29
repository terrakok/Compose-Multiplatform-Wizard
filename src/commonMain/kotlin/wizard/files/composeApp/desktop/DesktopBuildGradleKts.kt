package wizard.files.composeApp.desktop

import wizard.GradleModule
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.enableJvmHotReload
import wizard.isPlugin
import wizard.libraryNotation
import wizard.pluginNotation

class DesktopBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "desktopApp/build.gradle.kts"
    override val content = buildString {
        appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
        appendLine("")
        val plugins = info.dependencies.filter { it.modules.contains(GradleModule.DESKTOP) && it.isPlugin() }
        val libs = info.dependencies.filter { it.modules.contains(GradleModule.DESKTOP) && !it.isPlugin() }
        appendLine("plugins {")
        plugins.forEach { dep -> appendLine("    ${dep.pluginNotation}") }
        appendLine("}")
        appendLine("")
        appendLine("dependencies {")
        appendLine("    implementation(project(\":${info.moduleName}\"))")
        appendLine("    implementation(compose.ui)")
        libs.forEach { dep -> appendLine("    ${dep.libraryNotation}") }
        appendLine("}")
        appendLine("")
        appendLine("compose.desktop {")
        appendLine("    application {")
        appendLine("        mainClass = \"MainKt\"")
        appendLine("")
        appendLine("        nativeDistributions {")
        appendLine("            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)")
        appendLine("            packageName = \"${info.name}\"")
        appendLine("            packageVersion = \"1.0.0\"")
        appendLine("")
        appendLine("            linux {")
        appendLine("                iconFile.set(project.file(\"appIcons/LinuxIcon.png\"))")
        appendLine("            }")
        appendLine("            windows {")
        appendLine("                iconFile.set(project.file(\"appIcons/WindowsIcon.ico\"))")
        appendLine("            }")
        appendLine("            macOS {")
        appendLine("                iconFile.set(project.file(\"appIcons/MacosIcon.icns\"))")
        appendLine("                bundleID = \"${info.packageId}.desktopApp\"")
        appendLine("            }")
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
    }
}
