package wizard.files.composeApp

import wizard.*
import wizard.dependencies.*

class ModuleBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/build.gradle.kts"
    override val content = buildString {
        val plugins = mutableSetOf<Dependency>()
        val commonDeps = mutableSetOf<Dependency>()
        val otherDeps = mutableSetOf<Dependency>()
        val commonTestDeps = mutableSetOf<Dependency>()
        val otherTestDeps = mutableSetOf<Dependency>()
        val kspDeps = mutableSetOf<Dependency>()
        info.dependencies.forEach { dep ->
            when {
                dep.isPlugin() -> plugins.add(dep)

                dep.isKSP() -> {
                    kspDeps.add(dep)
                }

                dep.isCommon() -> {
                    if (!dep.isTestDependency) commonDeps.add(dep)
                    else commonTestDeps.add(dep)
                }

                else -> {
                    if (!dep.isTestDependency) otherDeps.add(dep)
                    else otherTestDeps.add(dep)
                }
            }
        }

        appendLine("import org.jetbrains.compose.ExperimentalComposeLibrary")
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("import org.jetbrains.compose.desktop.application.dsl.TargetFormat")
        }
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("import com.android.build.api.dsl.ManagedVirtualDevice")
            appendLine("import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi")
            appendLine("import org.jetbrains.kotlin.gradle.dsl.JvmTarget")
            appendLine("import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree")
        }
        appendLine("")
        appendLine("plugins {")
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation}")
        }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    androidTarget {")
            appendLine("        compilations.all {")
            appendLine("            compileTaskProvider {")
            appendLine("                compilerOptions {")
            appendLine("                    jvmTarget.set(JvmTarget.JVM_1_8)")
            appendLine("                    //https://jakewharton.com/gradle-toolchains-are-rarely-a-good-idea/#what-do-i-do")
            appendLine("                    freeCompilerArgs.add(\"-Xjdk-release=${'$'}{JavaVersion.VERSION_1_8}\")")
            appendLine("                }")
            appendLine("            }")
            appendLine("        }")
            appendLine("        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html")
            appendLine("        @OptIn(ExperimentalKotlinGradlePluginApi::class)")
            appendLine("        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)")
            appendLine("    }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("    jvm()")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js {")
            appendLine("        browser()")
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
            appendLine("            baseName = \"ComposeApp\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(compose.runtime)")
        appendLine("            implementation(compose.foundation)")
        appendLine("            implementation(compose.material3)")
        appendLine("            implementation(compose.components.resources)")
        appendLine("            implementation(compose.components.uiToolingPreview)")

        commonDeps.forEach { dep ->
            appendLine("            ${dep.libraryNotation}")
        }

        appendLine("        }")
        appendLine("")
        appendLine("        commonTest.dependencies {")
        appendLine("            implementation(kotlin(\"test\"))")
        appendLine("            @OptIn(ExperimentalComposeLibrary::class)")
        appendLine("            implementation(compose.uiTest)")

        commonTestDeps.forEach { dep ->
            appendLine("            ${dep.libraryNotation}")
        }

        appendLine("        }")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("        androidMain.dependencies {")
            appendLine("            implementation(compose.uiTooling)")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Android)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }
            appendLine("        }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("        jvmMain.dependencies {")
            appendLine("            implementation(compose.desktop.currentOs)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Jvm)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("        jsMain.dependencies {")
            appendLine("            implementation(compose.html.core)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Js)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("        iosMain.dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Ios)) {
                    appendLine("            ${dep.libraryNotation}")
                }
            }

            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("}")

        if (info.hasPlatform(ProjectPlatform.Android)) {
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
            appendLine("")
            appendLine("        testInstrumentationRunner = \"androidx.test.runner.AndroidJUnitRunner\"")
            appendLine("    }")
            appendLine("    //https://developer.android.com/studio/test/gradle-managed-devices")
            appendLine("    @Suppress(\"UnstableApiUsage\")")
            appendLine("    testOptions {")
            appendLine("        managedDevices.devices {")
            appendLine("            maybeCreate<ManagedVirtualDevice>(\"pixel5\").apply {")
            appendLine("                device = \"Pixel 5\"")
            appendLine("                apiLevel = 34")
            appendLine("                systemImageSource = \"aosp\"")
            appendLine("            }")
            appendLine("        }")
            appendLine("    }")
            appendLine("    compileOptions {")
            appendLine("        sourceCompatibility = JavaVersion.VERSION_1_8")
            appendLine("        targetCompatibility = JavaVersion.VERSION_1_8")
            appendLine("    }")
            appendLine("    buildFeatures {")
            appendLine("        //enables a Compose tooling support in the AndroidStudio")
            appendLine("        compose = true")
            appendLine("    }")
            appendLine("}")
            appendLine("")
            appendLine("//https://developer.android.com/develop/ui/compose/testing#setup")
            appendLine("dependencies {")
            appendLine("    androidTestImplementation(libs.androidx.uitest.junit4)")
            appendLine("    debugImplementation(libs.androidx.uitest.testManifest)")
            appendLine("}")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
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

        if (plugins.contains(RoomPlugin)) {
            appendLine("")
            appendLine("room {")
            appendLine("    schemaDirectory(\"\$projectDir/schemas\")")
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

        if (kspDeps.isNotEmpty()) {
            appendLine("")
            // FIXME: Added a hack to resolve below issue:
            // Cannot change attributes of configuration ':composeApp:debugFrameworkIosX64' after it has been locked for mutation
            // KSP is Not Work on IOS Only Project, Same For IosArm64, IosSimulatorArm64
            // To pass the testIosProject, put "ksp" related statements into "afterEvaluate".
            // However, it is not common to use only ios.
            val isIOSOnly = info.platforms == setOf(ProjectPlatform.Ios)
            if (isIOSOnly) {
                appendLine("afterEvaluate {")
                makeDependenciesForKsp(1, info, kspDeps)
                appendLine("}")
            } else {
                makeDependenciesForKsp(0, info, kspDeps)
            }
        }
    }


    /**
     * make KSP dependencies
     * @param[depth] if need append depth
     * @param[info] projectInfo
     * @param[kspDeps] ksp Dependency set
     */
    private fun StringBuilder.makeDependenciesForKsp(
        depth: Int,
        info: ProjectInfo,
        kspDeps: Set<Dependency>
    ) {
        val addDepth = "    ".repeat(depth)
        appendLine("${addDepth}dependencies {")
        kspDeps.forEach { dep ->
            appendLine("$addDepth    with(libs.${dep.catalogAccessor}) {")
            if (info.hasPlatform(ProjectPlatform.Android) && dep.hasPlatform(ProjectPlatform.Android)) {
                appendLine("$addDepth        add(\"kspAndroid\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Jvm) && dep.hasPlatform(ProjectPlatform.Jvm)) {
                appendLine("$addDepth        add(\"kspJvm\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Js) && dep.hasPlatform(ProjectPlatform.Js)) {
                appendLine("$addDepth        add(\"kspJs\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Wasm) && dep.hasPlatform(ProjectPlatform.Wasm)) {
                appendLine("$addDepth        add(\"kspWasmJs\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Ios) && dep.hasPlatform(ProjectPlatform.Ios)) {
                appendLine("$addDepth        add(\"kspIosX64\", this)")
                appendLine("$addDepth        add(\"kspIosArm64\", this)")
                appendLine("$addDepth        add(\"kspIosSimulatorArm64\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Macos) && dep.hasPlatform(ProjectPlatform.Macos)) {
                appendLine("$addDepth        add(\"kspMacosX64\", this)")
                appendLine("$addDepth        add(\"kspMacosArm64\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Linux) && dep.hasPlatform(ProjectPlatform.Linux)) {
                appendLine("$addDepth        add(\"kspLinuxX64\", this)")
            }
            if (info.hasPlatform(ProjectPlatform.Mingw) && dep.hasPlatform(ProjectPlatform.Mingw)) {
                appendLine("$addDepth        add(\"kspMingwX64\", this)")
            }
            appendLine("$addDepth    }")
        }
        appendLine("$addDepth}")
    }
}
