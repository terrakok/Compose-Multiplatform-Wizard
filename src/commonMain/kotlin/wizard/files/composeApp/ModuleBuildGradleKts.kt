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
        info.dependencies.forEach { dep ->
            when {
                dep.isPlugin() -> plugins.add(dep)
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
            appendLine("                    freeCompilerArgs.add(\"-Xjdk-release=${'$'}{JavaVersion.VERSION_1_8}\")")
            appendLine("                }")
            appendLine("            }")
            appendLine("        }")
            appendLine("        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html")
            appendLine("        @OptIn(ExperimentalKotlinGradlePluginApi::class)")
            appendLine("        instrumentedTestVariant {")
            appendLine("            sourceSetTree.set(KotlinSourceSetTree.test)")
            appendLine("            dependencies {")
            appendLine("                debugImplementation(libs.androidx.testManifest)")
            appendLine("                implementation(libs.androidx.junit4)")
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
        appendLine("        all {")
        appendLine("            languageSettings {")
        appendLine("                optIn(\"org.jetbrains.compose.resources.ExperimentalResourceApi\")")
        appendLine("            }")
        appendLine("        }")
        appendLine("        commonMain.dependencies {")
        appendLine("            implementation(compose.runtime)")
        appendLine("            implementation(compose.foundation)")
        appendLine("            implementation(compose.material3)")
        appendLine("            implementation(compose.components.resources)")
        appendLine("            implementation(compose.components.uiToolingPreview)")

        commonDeps.forEach { dep ->
            if (dep != RoomPluginCompiler) {
                appendLine("            ${dep.libraryNotation}")
            }
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
            appendLine("    sourceSets[\"main\"].apply {")
            appendLine("        manifest.srcFile(\"src/androidMain/AndroidManifest.xml\")")
            appendLine("        res.srcDirs(\"src/androidMain/res\")")
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
            appendLine("        compose = true")
            appendLine("    }")
            appendLine("    composeOptions {")
            appendLine("        kotlinCompilerExtensionVersion = \"${info.composeCompilerVersion}\"")
            appendLine("    }")
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

        if (info.hasPlatform(ProjectPlatform.Js) || info.hasPlatform(ProjectPlatform.Wasm)) {
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

        if (plugins.contains(RoomPlugin)) {
            appendLine("")
            appendLine("room {")
            appendLine("    schemaDirectory(\"\$projectDir/schemas\")")
            appendLine("}")
            appendLine("")
            appendLine("dependencies {")
            appendLine("    with(libs.room.compiler) {")
            if (info.hasPlatform(ProjectPlatform.Android)) {
                appendLine("        add(\"kspAndroid\",this)")
            }
            if (info.hasPlatform(ProjectPlatform.Ios)) {
                appendLine("        add(\"kspIosX64\",this)")
                appendLine("        add(\"kspIosArm64\",this)")
                appendLine("        add(\"kspIosSimulatorArm64\",this)")
            }
//            if (info.hasPlatform(ProjectPlatform.Jvm)) {
//                appendLine("        add(\"kspJvm\",this)")
//            }
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
