package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.catalogAccessor
import wizard.isPlugin

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            appendLine("    alias(libs.plugins.${dep.catalogAccessor}).apply(false)")
        }
        appendLine("}")
    }
}