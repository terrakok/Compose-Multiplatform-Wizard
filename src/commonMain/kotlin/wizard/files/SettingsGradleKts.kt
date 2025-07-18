package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.WizardType
import wizard.enableJvmHotReload
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

        if (info.enableJvmHotReload) {
            appendLine("")
            appendLine("""
                |plugins {
                |    //https://github.com/JetBrains/compose-hot-reload?tab=readme-ov-file#set-up-automatic-provisioning-of-the-jetbrains-runtime-jbr-via-gradle
                |    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
                |}
            """.trimMargin())
        }

        appendLine("")
        appendLine("include(\":${info.moduleName}\")")
        if (info.needComposeSample) appendLine("include(\":sample:composeApp\")")
        if (info.needTerminalSample) appendLine("include(\":sample:terminalApp\")")
        appendLine("")
    }
}