package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.safeName

class SettingsGradleKts(info: ProjectInfo, withConventionPlugins: Boolean) : ProjectFile {
    override val path = "settings.gradle.kts"
    override val content = buildString {
        appendLine("rootProject.name = \"${info.safeName}\"")
        appendLine("")
        appendLine("pluginManagement {")
        appendLine("    repositories {")
        appendLine("        google()")
        appendLine("        gradlePluginPortal()")
        appendLine("        mavenCentral()")
        appendLine("    }")
        appendLine("}")
        appendLine("")
        appendLine("dependencyResolutionManagement {")
        appendLine("    repositories {")
        appendLine("        google()")
        appendLine("        mavenCentral()")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/public/p/compose/dev\")")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental\")")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/public/p/ktor/eap\")")
        appendLine("    }")
        appendLine("}")
        appendLine("")
        if (withConventionPlugins) appendLine("includeBuild(\"convention-plugins\")")
        appendLine("include(\":${info.moduleName}\")")
        appendLine("")
    }
}