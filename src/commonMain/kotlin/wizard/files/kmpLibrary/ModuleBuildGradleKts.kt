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
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    jvmToolchain(17)")
            appendLine("")
            appendLine("    androidTarget { publishLibraryVariants(\"release\") }")
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
            appendLine("    iosX64()")
            appendLine("    iosArm64()")
            appendLine("    iosSimulatorArm64()")
        }
        if (info.hasPlatform(ProjectPlatform.Macos)) {
            appendLine("    macosX64()")
            appendLine("    macosArm64()")
        }
        if (info.hasPlatform(ProjectPlatform.Linux)) {
            appendLine("    linuxX64()")
        }
        if (info.hasPlatform(ProjectPlatform.Mingw)) {
            appendLine("    mingwX64()")
        }
        appendLine("")
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
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
            appendLine("        compilations[\"main\"].compileTaskProvider.configure {")
            appendLine("            compilerOptions {")
            appendLine("                freeCompilerArgs.add(\"-Xexport-kdoc\")")
            appendLine("            }")
            appendLine("        }")
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
            appendLine("}")
        }

        appendLine("")
        appendLine("//Publishing your Kotlin Multiplatform library to Maven Central")
        appendLine("//https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html")
        appendLine("mavenPublishing {")
        appendLine("    publishToMavenCentral()")
        appendLine("    coordinates(\"${info.packageId}\", \"${info.moduleName}\", \"1.0.0\")")
        appendLine("")
        appendLine("    pom {")
        appendLine("        name = \"${info.name}\"")
        appendLine("        description = \"Kotlin Multiplatform library\"")
        appendLine("        url = \"github url\" //todo")
        appendLine("")
        appendLine("        licenses {")
        appendLine("            license {")
        appendLine("                name = \"MIT\"")
        appendLine("                url = \"https://opensource.org/licenses/MIT\"")
        appendLine("            }")
        appendLine("        }")
        appendLine("")
        appendLine("        developers {")
        appendLine("            developer {")
        appendLine("                id = \"\" //todo")
        appendLine("                name = \"\" //todo")
        appendLine("                email = \"\" //todo")
        appendLine("            }")
        appendLine("        }")
        appendLine("")
        appendLine("        scm {")
        appendLine("            url = \"github url\" //todo")
        appendLine("        }")
        appendLine("    }")
        appendLine("    if (project.hasProperty(\"signing.keyId\")) signAllPublications()")
        appendLine("}")

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
