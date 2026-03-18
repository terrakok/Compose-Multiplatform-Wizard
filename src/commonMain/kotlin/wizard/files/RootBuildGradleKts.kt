package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.WizardType
import wizard.catalogAccessor
import wizard.hasPlatform
import wizard.isPlugin
import wizard.needComposeSample

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        info.dependencies.filter { it.isPlugin() }.forEach { dep ->
            appendLine("    alias(libs.plugins.${dep.catalogAccessor}).apply(false)")
        }
        if (info.needComposeSample) {
            appendLine("    alias(libs.plugins.kotlin.jvm).apply(false)")
            if (info.hasPlatform(ProjectPlatform.Android)) {
                appendLine("    alias(libs.plugins.kotlin.android).apply(false)")
                appendLine("    alias(libs.plugins.android.application).apply(false)")
            }
        }
        appendLine("}")
    }
}