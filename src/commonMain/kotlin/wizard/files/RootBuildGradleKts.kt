package wizard.files

import wizard.ComposePlatform
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.hasAndroid

class RootBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    kotlin(\"multiplatform\").apply(false)")
        if (info.hasAndroid) {
            appendLine("    id(\"com.android.application\").apply(false)")
        }
        appendLine("}")
    }
}