package wizard.files

import wizard.dependencies.KotlinxSerializationPlugin
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.dependencies.AndroidApplicationPlugin
import wizard.dependencies.AndroidxActivityCompose
import wizard.dependencies.ComposeCompilerPlugin
import wizard.dependencies.ComposeFoundation
import wizard.dependencies.ComposeMultiplatformPlugin
import wizard.dependencies.ComposeRuntime
import wizard.dependencies.ComposeUi
import wizard.hasPlatform
import wizard.isPlugin
import wizard.needComposeSample

class GradleLibsVersion(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/libs.versions.toml"
    override val content = buildString {
        val dependencies = buildSet {
            addAll(info.dependencies)
            if (info.needComposeSample) {
                add(ComposeMultiplatformPlugin)
                add(ComposeCompilerPlugin)
                add(ComposeRuntime)
                add(ComposeUi)
                add(ComposeFoundation)
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
