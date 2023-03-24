package wizard.files

import wizard.*

class SettingsGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "settings.gradle.kts"
    override val content = buildString {
        appendLine("rootProject.name = \"${info.name}\"")
        appendLine("include(\":composeApp\")")
        appendLine("")
        appendLine("pluginManagement {")
        appendLine("    repositories {")
        appendLine("        google()")
        appendLine("        gradlePluginPortal()")
        appendLine("        mavenCentral()")
        appendLine("        maven(\"https://maven.pkg.jetbrains.space/public/p/compose/dev\")")
        appendLine("    }")
        appendLine("    plugins {")
        appendLine("        val kotlin = \"${info.kotlinVersion}\"")
        appendLine("        kotlin(\"android\").version(kotlin)")
        appendLine("        kotlin(\"multiplatform\").version(kotlin)")
        appendLine("        kotlin(\"native.cocoapods\").version(kotlin)")
        appendLine("")
        appendLine("        id(\"com.android.application\").version(\"${info.agpVersion}\")")
        appendLine("        id(\"org.jetbrains.compose\").version(\"${info.composeVersion}\")")

        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            if (dep == KotlinxSerializationPlugin) {
                appendLine("        ${dep.pluginNotation()}.version(kotlin)")
            } else {
                appendLine("        ${dep.pluginNotation()}.version(\"${dep.version}\")")
            }
        }

        appendLine("")
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