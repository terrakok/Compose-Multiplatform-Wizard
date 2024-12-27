package wizard.files.kmpLibrary.sample

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

class TerminalAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/terminalApp/build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.multiplatform)")
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        appendLine("    listOf(")
        if (info.hasPlatform(ProjectPlatform.Macos)) {
            appendLine("        macosX64(),")
            appendLine("        macosArm64(),")
        }
        if (info.hasPlatform(ProjectPlatform.Linux)) {
            appendLine("        linuxX64(),")
        }
        if (info.hasPlatform(ProjectPlatform.Mingw)) {
            appendLine("        mingwX64(),")
        }
        appendLine("    ).forEach {")
        appendLine("        it.binaries.executable {")
        appendLine("            entryPoint = \"main\"")
        appendLine("        }")
        appendLine("    }")
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(project(\":${info.moduleName}\"))")
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
    }
}


class TerminalAppMainKt(info: ProjectInfo) : ProjectFile {
    override val path = "sample/terminalApp/src/commonMain/kotlin/main.kt"
    override val content = """
        import ${info.packageId}.getFibonacciNumbers

        fun main() {
            println("getFibonacciNumbers(7)=${'$'}{getFibonacciNumbers(7).joinToString(", ")}")
        }
    """.trimIndent()
}