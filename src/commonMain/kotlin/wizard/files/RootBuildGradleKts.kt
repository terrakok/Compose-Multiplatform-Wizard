package wizard.files

import wizard.*

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("        kotlin(\"multiplatform\").version(libs.versions.kotlin.get()).apply(false)")
        appendLine("        kotlin(\"native.cocoapods\").version(libs.versions.kotlin.get()).apply(false)")
        if(info.hasAndroid) {
            appendLine("        kotlin(\"android\").version(libs.versions.kotlin.get()).apply(false)")
            appendLine("        alias(libs.plugins.android.application).apply(false)")
            appendLine("        alias(libs.plugins.android.library).apply(false)")
        }
        appendLine("        alias(libs.plugins.compose).apply(false)")

        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            if (dep == KotlinxSerializationPlugin) {
                appendLine("        ${dep.pluginNotation()}.version(libs.versions.kotlin.get()).apply(false)")
            } else {
                appendLine("        alias(libs.plugins.${dep.versionCatalog.libraryAccessor}).apply(false)")
            }
        }

        appendLine("}")
    }
}