package wizard.files

import wizard.dependencies.KotlinxSerializationPlugin
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.WizardType
import wizard.dependencies.AndroidApplicationPlugin
import wizard.dependencies.AndroidxActivityCompose
import wizard.dependencies.ComposeCompilerPlugin
import wizard.dependencies.ComposePlugin
import wizard.hasPlatform
import wizard.isPlugin
import wizard.needComposeSample

class GradleLibsVersion(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/libs.versions.toml"
    override val content = buildString {
        val dependencies = buildSet {
            addAll(info.dependencies)
            if (info.needComposeSample) {
                add(ComposePlugin)
                add(ComposeCompilerPlugin)
                if (info.hasPlatform(ProjectPlatform.Android)) {
                    add(AndroidApplicationPlugin)
                    add(AndroidxActivityCompose)
                }
            }
        }

        // versions
        appendLine("[versions]")
        appendLine()
        dependencies
            .filter { it != KotlinxSerializationPlugin }
            .distinctBy { it.catalogVersionName }
            .forEach {
                appendLine("${it.catalogVersionName} = \"${it.version}\"")
            }
        appendLine()

        // libraries
        appendLine("[libraries]")
        appendLine()
        dependencies.filterNot { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { module = \"${it.group}:${it.id}\", version.ref = \"${it.catalogVersionName}\" }")
        }
        appendLine()

        // plugins
        appendLine("[plugins]")
        appendLine()
        dependencies.filter { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { id = \"${it.group}\", version.ref = \"${it.catalogVersionName}\" }")
        }
    }
}
