package wizard.files.kmpLibrary

import wizard.*
import wizard.dependencies.*

class ModuleBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/build.gradle.kts"
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

        appendLine("plugins {")
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation}")
        }
        appendLine("    id(\"convention.publication\")")
        appendLine("}")
        appendLine("")
        appendLine("group = \"${info.packageId}\"")
        appendLine("version = \"1.0\"")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    androidTarget {")
            appendLine("        publishLibraryVariants(\"release\")")
            appendLine("        compilations.all {")
            appendLine("            kotlinOptions {")
            appendLine("                jvmTarget = \"17\"")
            appendLine("            }")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("    jvm()")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js {")
            appendLine("        browser {")
            appendLine("            webpackTask {")
            appendLine("                mainOutputFileName = \"${info.moduleName}.js\"")
            appendLine("            }")
            appendLine("        }")
            appendLine("        binaries.executable()")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("    wasmJs {")
            appendLine("        browser()")
            appendLine("        binaries.executable()")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    listOf(")
            appendLine("        iosX64(),")
            appendLine("        iosArm64(),")
            appendLine("        iosSimulatorArm64()")
            appendLine("    ).forEach {")
            appendLine("        it.binaries.framework {")
            appendLine("            baseName = \"${info.moduleName}\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Macos)) {
            appendLine("    listOf(")
            appendLine("        macosX64(),")
            appendLine("        macosArm64()")
            appendLine("    ).forEach {")
            appendLine("        it.binaries.framework {")
            appendLine("            baseName = \"${info.moduleName}\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Linux)) {
            appendLine("    linuxX64 {")
            appendLine("        binaries.staticLib {")
            appendLine("            baseName = \"${info.moduleName}\"")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Mingw)) {
            appendLine("    mingwX64 {")
            appendLine("        binaries.staticLib {")
            appendLine("            baseName = \"${info.moduleName}\"")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("        commonTest.dependencies {")
        appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight:1.0.1\")")
        appendLine("        }")
        commonDeps.forEach { dep ->
            appendLine("            ${dep.libraryNotation}")
        }
        appendLine("        }")
        appendLine("")
        appendLine("        commonTest.dependencies {")
        appendLine("            implementation(kotlin(\"test\"))")
        appendLine("        }")
        appendLine("")
        if (info.hasDependenciesFor(ProjectPlatform.Android)) {
            appendLine("        androidMain.dependencies {")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight:1.0.1\")")
            appendLine("        }")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Android)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Jvm)) {
            appendLine("        jvmMain.dependencies {")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-desktop:1.0.1\")")
            appendLine("        }")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Jvm)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Js)) {
            appendLine("        jsMain.dependencies {")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-wasm-js:1.0.1\")")
            appendLine("        }")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Js)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Wasm)) {
            appendLine("        getByName(\"wasmJsMain\").dependencies {")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-wasm-js:1.0.1\")")
            appendLine("        }")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Wasm)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Ios)) {
            appendLine("        iosMain.dependencies {")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-iosx64:1.0.1\")")
            appendLine("        }")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-iosarm64:1.0.1\")")
            appendLine("        }")
            appendLine("            implementation(\"com.akexorcist.kotlin.multiplatform:dayandnight-iossimulatorarm64:1.0.1\")")
            appendLine("        }")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Ios)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Macos)) {
            appendLine("        macosMain.dependencies {")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Macos)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Linux)) {
            appendLine("        linuxMain.dependencies {")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Linux)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDependenciesFor(ProjectPlatform.Mingw)) {
            appendLine("        mingwMain.dependencies {")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Mingw)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("")
        if (info.hasNativePlatforms()) {
            appendLine("    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers")
            appendLine("    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {")
            appendLine("        compilations[\"main\"].compilerOptions.options.freeCompilerArgs.add(\"-Xexport-kdoc\")")
            appendLine("    }")
            appendLine("")
        }
        appendLine("}")

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("")
            appendLine("android {")
            appendLine("    namespace = \"${info.packageId}\"")
            appendLine("    compileSdk = ${info.androidTargetSdk}")
            appendLine("")
            appendLine("    defaultConfig {")
            appendLine("        minSdk = ${info.androidMinSdk}")
            appendLine("    }")
            appendLine("    compileOptions {")
            appendLine("        sourceCompatibility = JavaVersion.VERSION_17")
            appendLine("        targetCompatibility = JavaVersion.VERSION_17")
            appendLine("    }")
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
    }
}

private fun ProjectInfo.hasDependenciesFor(platform: ProjectPlatform) =
    hasPlatform(platform) && dependencies.any { dep ->
        dep.platforms.contains(platform)
    }

private fun ProjectInfo.hasNativePlatforms() =
    platforms.any {
        it == ProjectPlatform.Ios
                || it == ProjectPlatform.Macos
                || it == ProjectPlatform.Linux
                || it == ProjectPlatform.Mingw
    }
