package wizard.files.kmpLibrary

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform
import wizard.hasWebPlatform
import wizard.needComposeSample
import wizard.needTerminalSample
import wizard.safeName

class SettingsGradleKts(info: ProjectInfo) : ProjectFile {
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
        appendLine("include(\":${info.moduleName}\")")
        if (info.needComposeSample) {
            appendLine("include(\":sample:sharedUI\")")
            if (info.hasPlatform(ProjectPlatform.Android)) appendLine("include(\":sample:androidApp\")")
            if (info.hasPlatform(ProjectPlatform.Jvm)) appendLine("include(\":sample:desktopApp\")")
            if (info.hasWebPlatform()) appendLine("include(\":sample:webApp\")")
        }
        if (info.needTerminalSample) appendLine("include(\":sample:terminalApp\")")
        appendLine("")
    }
}