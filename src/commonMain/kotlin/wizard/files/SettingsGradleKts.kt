package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.safeName

class SettingsGradleKts(info: ProjectInfo, withConventionPlugins: Boolean) : ProjectFile {
    override val path = "settings.gradle.kts"
    override val content = buildString {
        appendLine("rootProject.name = \"${info.safeName}\"")
        appendLine("")
        append("""
            |pluginManagement {
            |    repositories {
            |        google {
            |            content { 
            |              	includeGroupByRegex("com\\.android.*")
            |              	includeGroupByRegex("com\\.google.*")
            |              	includeGroupByRegex("androidx.*")
            |              	includeGroupByRegex("android.*")
            |            }
            |        }
            |        gradlePluginPortal()
            |        mavenCentral()
            |    }
            |}
            |
            |dependencyResolutionManagement {
            |    repositories {
            |        google {
            |            content { 
            |              	includeGroupByRegex("com\\.android.*")
            |              	includeGroupByRegex("com\\.google.*")
            |              	includeGroupByRegex("androidx.*")
            |              	includeGroupByRegex("android.*")
            |            }
            |        }
            |        mavenCentral()
            |    }
            |}
        """.trimMargin())
        appendLine("")
        if (withConventionPlugins) appendLine("includeBuild(\"convention-plugins\")")
        appendLine("include(\":${info.moduleName}\")")
        appendLine("")
    }
}