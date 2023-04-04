package wizard.files

import wizard.KotlinxSerializationPlugin
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.agpVersionRef
import wizard.composeVersionRef
import wizard.isPlugin
import wizard.kotlinVersionRef

class GradleLibsVersion(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/libs.versions.toml"
    override val content = buildString {
        // versions
        fun versionRef(versionRef: String, version: String) = "$versionRef = \"${version}\""
        val versions = info.dependencies.distinctBy { it.versionCatalog.versionRefName }

        appendLine("[versions]")
        appendLine()

        appendLine(versionRef(info.composeVersionRef, info.composeVersion))
        appendLine(versionRef(info.kotlinVersionRef, info.kotlinVersion))
        appendLine(versionRef(info.agpVersionRef, info.agpVersion))

        for (dependency in versions) {
            val config = dependency.versionCatalog
            if(config.versionRefName.isNotEmpty()) {
                appendLine(versionRef(config.versionRefName, dependency.version))
            }
        }

        // libraries
        val libraries = info.dependencies.filterNot { it.isPlugin() }
        appendLine()
        appendLine("[libraries]")
        appendLine()

        for (dependency in libraries) {
            val config = dependency.versionCatalog
            appendLine("${config.libraryName} = { module = \"${dependency.group}:${dependency.id}\", version.ref = \"${config.versionRefName}\" }")
        }

        // plugins
        fun plugin(pluginName: String, id: String, versionRef: String) =
            "$pluginName = { id = \"${id}\", version.ref = \"${versionRef}\" }"
        val plugins = info.dependencies
            .filter { it.isPlugin() }
            .filterNot { it == KotlinxSerializationPlugin }

        appendLine()
        appendLine("[plugins]")
        appendLine()

        appendLine(plugin("compose", "org.jetbrains.compose", info.composeVersionRef))
        appendLine(plugin("android-application", "com.android.application", info.agpVersionRef))
        appendLine(plugin("android-library", "com.android.library", info.agpVersionRef))


        for (dependency in plugins) {
            val config = dependency.versionCatalog
            appendLine(plugin(config.libraryName, dependency.group, config.versionRefName))
        }


    }
}
