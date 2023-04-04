package wizard.files

import wizard.*

class SettingsGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "settings.gradle.kts"
    override val content = buildString {
        appendLine("rootProject.name = \"${info.safeName}\"")
        appendLine("include(\":composeApp\")")
        appendLine("")
        appendLine("pluginManagement {")
        appendLine("    repositories {")
        appendLine("        google()")
        appendLine("        gradlePluginPortal()")
        appendLine("        mavenCentral()")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/public/p/compose/dev\")")
        appendLine("    }")
        appendLine("}")
        appendLine("")
        appendLine("dependencyResolutionManagement {")
        appendLine("    repositories {")
        appendLine("        google()")
        appendLine("        mavenCentral()")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/public/p/compose/dev\")")
        appendLine("    }")
        appendLine("}")
    }
}