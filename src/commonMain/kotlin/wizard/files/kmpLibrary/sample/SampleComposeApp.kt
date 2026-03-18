package wizard.files.kmpLibrary.sample

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

import wizard.hasWebPlatform

class SampleSharedUIBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/sharedUI/build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.kotlin.multiplatform)")
        appendLine("    alias(libs.plugins.compose.multiplatform)")
        appendLine("    alias(libs.plugins.compose.compiler)")
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    androidTarget()")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("    jvm()")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js { browser() }")
        }
        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("    wasmJs { browser() }")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    iosArm64()")
            appendLine("    iosSimulatorArm64()")
        }
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(libs.compose.runtime)")
        appendLine("            implementation(libs.compose.ui)")
        appendLine("            implementation(libs.compose.foundation)")
        appendLine("            implementation(project(\":${info.moduleName}\"))")
        appendLine("        }")
        appendLine("    }")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {")
            appendLine("        binaries.framework {")
            appendLine("            baseName = \"ComposeApp\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
        }
        appendLine("}")
    }
}

class SampleAndroidAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/androidApp/build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.kotlin.android)")
        appendLine("    alias(libs.plugins.android.application)")
        appendLine("    alias(libs.plugins.compose.multiplatform)")
        appendLine("    alias(libs.plugins.compose.compiler)")
        appendLine("}")
        appendLine("")
        appendLine("android {")
        appendLine("    namespace = \"${info.packageId}.sample\"")
        appendLine("    compileSdk = ${info.androidTargetSdk}")
        appendLine("")
        appendLine("    defaultConfig {")
        appendLine("        minSdk = ${info.androidMinSdk}")
        appendLine("        targetSdk = ${info.androidTargetSdk}")
        appendLine("")
        appendLine("        applicationId = \"${info.packageId}.sample\"")
        appendLine("        versionCode = 1")
        appendLine("        versionName = \"1.0.0\"")
        appendLine("    }")
        appendLine("    ")
        appendLine("    compileOptions {")
        appendLine("        sourceCompatibility = JavaVersion.VERSION_17")
        appendLine("        targetCompatibility = JavaVersion.VERSION_17")
        appendLine("    }")
        appendLine("}")
        appendLine("")
        appendLine("dependencies {")
        appendLine("    implementation(project(\":sample:sharedUI\"))")
        appendLine("    implementation(libs.androidx.activity.compose)")
        appendLine("}")
    }
}

class SampleDesktopAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/desktopApp/build.gradle.kts"
    override val content = buildString {
        appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
        appendLine("")
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.kotlin.multiplatform)")
        appendLine("    alias(libs.plugins.compose.multiplatform)")
        appendLine("    alias(libs.plugins.compose.compiler)")
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        appendLine("    jvm()")
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        jvmMain.dependencies {")
        appendLine("            implementation(project(\":sample:sharedUI\"))")
        appendLine("            implementation(compose.desktop.currentOs)")
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
        appendLine("")
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

class SampleWebAppBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "sample/webApp/build.gradle.kts"
    override val content = buildString {
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.kotlin.multiplatform)")
        appendLine("    alias(libs.plugins.compose.multiplatform)")
        appendLine("    alias(libs.plugins.compose.compiler)")
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js {")
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
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(project(\":sample:sharedUI\"))")
        appendLine("        }")
        appendLine("    }")
        appendLine("}")
    }
}