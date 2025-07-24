package wizard.files.kmpLibrary.sample

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

class SampleComposeAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/composeApp/build.gradle.kts"
    override val content = buildString {
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
            appendLine("")
        }
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.multiplatform)")
        appendLine("    alias(libs.plugins.compose.compiler)")
        appendLine("    alias(libs.plugins.compose)")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    alias(libs.plugins.android.application)")
        }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    jvmToolchain(17)")
            appendLine("")
            appendLine("    androidTarget()")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("    jvm()")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js  {")
            appendLine("        browser()")
            appendLine("        binaries.executable()")
            appendLine("    }")
        }
        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("    wasmJs {")
            appendLine("        browser()")
            appendLine("        binaries.executable()")
            appendLine("    }")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    listOf(")
            appendLine("        iosX64(),")
            appendLine("        iosArm64(),")
            appendLine("        iosSimulatorArm64()")
            appendLine("    ).forEach {")
            appendLine("        it.binaries.framework {")
            appendLine("            baseName = \"ComposeApp\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
        }
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(compose.runtime)")
        appendLine("            implementation(compose.ui)")
        appendLine("            implementation(compose.foundation)")
        appendLine("            implementation(project(\":${info.moduleName}\"))")
        appendLine("        }")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("        androidMain.dependencies {")
            appendLine("            implementation(libs.androidx.activityCompose)")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("        jvmMain.dependencies {")
            appendLine("            implementation(compose.desktop.currentOs)")
            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("}")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("android {")
            appendLine("    namespace = \"sample.app\"")
            appendLine("    compileSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("    defaultConfig {")
            appendLine("        minSdk = ${info.androidMinSdk}")
            appendLine("        targetSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("        applicationId = \"sample.app.androidApp\"")
            appendLine("        versionCode = 1")
            appendLine("        versionName = \"1.0.0\"")
            appendLine("    }")
            appendLine("}")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("compose.desktop {")
            appendLine("    application {")
            appendLine("        mainClass = \"MainKt\"")
            appendLine("")
            appendLine("        nativeDistributions {")
            appendLine("            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)")
            appendLine("            packageName = \"sample\"")
            appendLine("            packageVersion = \"1.0.0\"")
            appendLine("        }")
            appendLine("    }")
            appendLine("}")
        }
    }
}