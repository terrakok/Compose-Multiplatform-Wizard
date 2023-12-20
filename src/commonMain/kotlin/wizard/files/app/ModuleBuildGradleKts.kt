package wizard.files.app

import wizard.ApolloPlugin
import wizard.BuildConfigPlugin
import wizard.BuildKonfigPlugin
import wizard.ComposePlatform
import wizard.Dependency
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.SQLDelightPlugin
import wizard.hasAndroid
import wizard.hasBrowser
import wizard.hasDesktop
import wizard.hasIos
import wizard.isCommon
import wizard.isPlugin
import wizard.libraryNotation
import wizard.pluginNotation

class ModuleBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/build.gradle.kts"
    override val content = buildString {
        val plugins = mutableSetOf<Dependency>()
        val commonDeps = mutableSetOf<Dependency>()
        val otherDeps = mutableSetOf<Dependency>()
        info.dependencies.forEach { dep ->
            when {
                dep.isPlugin() -> plugins.add(dep)
                dep.isCommon() -> commonDeps.add(dep)
                else -> otherDeps.add(dep)
            }
        }


        if (info.hasDesktop) {
            appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
            appendLine("")
        }
        appendLine("plugins {")
        appendLine("    alias(libs.plugins.multiplatform)")
        appendLine("    alias(libs.plugins.compose)")
        if (info.hasAndroid) {
            appendLine("    alias(libs.plugins.android.application)")
        }
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation}")
        }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasAndroid) {
            appendLine("    androidTarget {")
            appendLine("        compilations.all {")
            appendLine("            kotlinOptions {")
            appendLine("                jvmTarget = \"17\"")
            appendLine("            }")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("    jvm()")
            appendLine("")
        }
        if (info.hasBrowser) {
            appendLine("    js {")
            appendLine("        browser()")
            appendLine("        binaries.executable()")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasIos) {
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
            appendLine("")
        }
        appendLine("    sourceSets {")
        appendLine("        all {")
        appendLine("            languageSettings {")
        appendLine("                optIn(\"org.jetbrains.compose.resources.ExperimentalResourceApi\")")
        appendLine("            }")
        appendLine("        }")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(compose.runtime)")
        appendLine("            implementation(compose.material3)")
        appendLine("            implementation(compose.materialIconsExtended)")
        appendLine("            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)")
        appendLine("            implementation(compose.components.resources)")

        commonDeps.forEach { dep ->
            appendLine("            ${dep.libraryNotation}")
        }

        appendLine("        }")
        appendLine("")
        appendLine("        commonTest.dependencies {")
        appendLine("            implementation(kotlin(\"test\"))")
        appendLine("        }")
        appendLine("")
        if (info.hasAndroid) {
            appendLine("        androidMain.dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Android)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("        jvmMain.dependencies {")
            appendLine("            implementation(compose.desktop.common)")
            appendLine("            implementation(compose.desktop.currentOs)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Desktop)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        if (info.hasBrowser) {
            appendLine("        jsMain.dependencies {")
            appendLine("            implementation(compose.html.core)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Browser)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        if (info.hasIos) {
            appendLine("        iosMain.dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Ios)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("}")

        if (info.hasAndroid) {
            appendLine("")
            appendLine("android {")
            appendLine("    namespace = \"${info.packageId}\"")
            appendLine("    compileSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("    defaultConfig {")
            appendLine("        minSdk = ${info.androidMinSdk}")
            appendLine("        targetSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("        applicationId = \"${info.packageId}.androidApp\"")
            appendLine("        versionCode = 1")
            appendLine("        versionName = \"1.0.0\"")
            appendLine("    }")
            appendLine("    sourceSets[\"main\"].apply {")
            appendLine("        manifest.srcFile(\"src/androidMain/AndroidManifest.xml\")")
            appendLine("        res.srcDirs(\"src/androidMain/resources\")")
            appendLine("        resources.srcDirs(\"src/commonMain/resources\")")
            appendLine("    }")
            appendLine("    compileOptions {")
            appendLine("        sourceCompatibility = JavaVersion.VERSION_17")
            appendLine("        targetCompatibility = JavaVersion.VERSION_17")
            appendLine("    }")
            appendLine("    buildFeatures {")
            appendLine("        compose = true")
            appendLine("    }")
            appendLine("    composeOptions {")
            appendLine("        kotlinCompilerExtensionVersion = \"${info.composeCompilerVersion}\"")
            appendLine("    }")
            appendLine("}")
        }
        if (info.hasDesktop) {
            appendLine("")
            appendLine("compose.desktop {")
            appendLine("    application {")
            appendLine("        mainClass = \"MainKt\"")
            appendLine("")
            appendLine("        nativeDistributions {")
            appendLine("            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)")
            appendLine("            packageName = \"${info.packageId}.desktopApp\"")
            appendLine("            packageVersion = \"1.0.0\"")
            appendLine("        }")
            appendLine("    }")
            appendLine("}")
        }

        if (info.hasBrowser) {
            appendLine("")
            appendLine("compose.experimental {")
            appendLine("    web.application {}")
            appendLine("}")
        }

        if (plugins.contains(BuildConfigPlugin)) {
            appendLine("")
            appendLine("buildConfig {")
            appendLine("    // BuildConfig configuration here.")
            appendLine("    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts")
            appendLine("}")
        }

        if (plugins.contains(BuildKonfigPlugin)) {
            appendLine("")
            appendLine("buildkonfig {")
            appendLine("    // BuildKonfig configuration here.")
            appendLine("    // https://github.com/yshrsmz/BuildKonfig#gradle-configuration")
            appendLine("    packageName = \"${info.packageId}\"")
            appendLine("    defaultConfigs {")
            appendLine("    }")
            appendLine("}")
        }

        if (plugins.contains(SQLDelightPlugin)) {
            appendLine("")
            appendLine("sqldelight {")
            appendLine("    databases {")
            appendLine("        create(\"MyDatabase\") {")
            appendLine("            // Database configuration here.")
            appendLine("            // https://cashapp.github.io/sqldelight")
            appendLine("            packageName.set(\"${info.packageId}.db\")")
            appendLine("        }")
            appendLine("    }")
            appendLine("}")
        }

        if (plugins.contains(ApolloPlugin)) {
            appendLine("")
            appendLine("apollo {")
            appendLine("    service(\"api\") {")
            appendLine("        // GraphQL configuration here.")
            appendLine("        // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/")
            appendLine("        packageName.set(\"${info.packageId}.graphql\")")
            appendLine("    }")
            appendLine("}")
        }
    }
}
