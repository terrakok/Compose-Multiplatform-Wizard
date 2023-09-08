package wizard.files

import wizard.*

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.multiplatform).apply(false)")
        appendLine("    alias(libs.plugins.compose).apply(false)")
        if (info.hasAndroid) {
            appendLine("    alias(libs.plugins.android.application).apply(false)")
        }

        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            appendLine("    alias(libs.plugins.${dep.catalogAccessor}).apply(false)")
        }

        appendLine("}")
    }
}