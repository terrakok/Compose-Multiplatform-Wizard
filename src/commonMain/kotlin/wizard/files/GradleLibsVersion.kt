package wizard.files

import wizard.KotlinxSerializationPlugin
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.isPlugin

class GradleLibsVersion(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/libs.versions.toml"
    override val content = buildString {
        // versions
        appendLine("[versions]")
        appendLine()
        info.dependencies
            .filter { it != KotlinxSerializationPlugin }
            .distinctBy { it.catalogVersionName }
            .forEach {
                appendLine("${it.catalogVersionName} = \"${it.version}\"")
            }
        appendLine()

        // libraries
        appendLine("[libraries]")
        appendLine()
        info.dependencies.filterNot { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { module = \"${it.group}:${it.id}\", version.ref = \"${it.catalogVersionName}\" }")
        }
        appendLine()

        // plugins
        appendLine("[plugins]")
        appendLine()
        info.dependencies.filter { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { id = \"${it.group}\", version.ref = \"${it.catalogVersionName}\" }")
        }
    }
}
