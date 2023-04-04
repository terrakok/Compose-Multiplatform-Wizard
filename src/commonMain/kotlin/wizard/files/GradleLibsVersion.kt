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

        appendLine("kotlin = \"${info.kotlinVersion}\"")
        appendLine("agp = \"${info.agpVersion}\"")
        appendLine("compose = \"${info.composeVersion}\"")

        info.dependencies
            .filter { it != KotlinxSerializationPlugin }
            .distinctBy { it.catalogVersionName }
            .forEach {
                appendLine("${it.catalogVersionName} = \"${it.version}\"")
            }
        appendLine()

        // libraries
        val libraries = info.dependencies.filterNot { it.isPlugin() }
        appendLine("[libraries]")
        appendLine()

        info.dependencies.filterNot { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { module = \"${it.group}:${it.id}\", version.ref = \"${it.catalogVersionName}\" }")
        }
        appendLine()

        // plugins
        appendLine("[plugins]")
        appendLine()

        appendLine("multiplatform = { id = \"org.jetbrains.kotlin.multiplatform\", version.ref = \"kotlin\" }")
        appendLine("cocoapods = { id = \"org.jetbrains.kotlin.native.cocoapods\", version.ref = \"kotlin\" }")
        appendLine("compose = { id = \"org.jetbrains.compose\", version.ref = \"compose\" }")
        appendLine("android-application = { id = \"com.android.application\", version.ref = \"agp\" }")

        info.dependencies.filter { it.isPlugin() }.forEach {
            appendLine("${it.catalogName} = { id = \"${it.group}\", version.ref = \"${it.catalogVersionName}\" }")
        }
    }
}
