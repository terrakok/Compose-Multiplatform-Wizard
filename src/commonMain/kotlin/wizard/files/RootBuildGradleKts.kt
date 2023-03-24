package wizard.files

import wizard.*

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    kotlin(\"multiplatform\").apply(false)")
        if (info.hasAndroid) {
            appendLine("    id(\"com.android.application\").apply(false)")
        }

        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            appendLine("    ${dep.pluginNotation()}.apply(false)")
        }

        appendLine("}")
    }
}