package wizard.files.composeApp.shared

import wizard.*
import wizard.dependencies.*

class SharedBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/build.gradle.kts"
    override val content = buildString {
        val plugins = mutableSetOf<Dependency>()
        val commonDeps = mutableSetOf<Dependency>()
        val otherDeps = mutableSetOf<Dependency>()
        val commonTestDeps = mutableSetOf<Dependency>()
        val otherTestDeps = mutableSetOf<Dependency>()
        val kspDeps = mutableSetOf<Dependency>()
        info.dependencies.filter { it.modules.contains(GradleModule.SHARED) }.forEach { dep ->
            when {
                dep.isPlugin() -> plugins.add(dep)

                dep.isKSP() -> kspDeps.add(dep)

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
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("import org.jetbrains.kotlin.gradle.dsl.JvmTarget")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("import org.gradle.kotlin.dsl.withType")
            appendLine("import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget")
        }
        if (isNotEmpty()) appendLine("")
        appendLine("plugins {")
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation}")
        }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("    androidTarget() //We need the deprecated target to have working previews")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("    jvm()")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("    js { browser() }")
        }
        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("    wasmJs { browser() }")
            appendLine("")
        }
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    iosX64()")
            appendLine("    iosArm64()")
            appendLine("    iosSimulatorArm64()")
            appendLine("")
        }
        appendLine("    sourceSets {")
        appendLine("        commonMain.dependencies {")
        commonDeps.forEach { dep ->
            appendLine("            ${dep.useApiInsteadOfImplementationForComposeDeps()}")
        }

        appendLine("        }")
        appendLine("")
        appendLine("        commonTest.dependencies {")
        appendLine("            implementation(kotlin(\"test\"))")
        commonTestDeps.forEach { dep ->
            appendLine("            ${dep.libraryNotation}")
        }

        appendLine("        }")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("        androidMain.dependencies {")
            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ProjectPlatform.Android) && dep != ComposeUiTooling) {
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
        val webDeps = otherDeps.filter { it.isWeb() }
        if (info.hasWebPlatform() && webDeps.isNotEmpty()) {
            appendLine("        webMain.dependencies {")
            webDeps.forEach { dep ->
                appendLine("            ${dep.libraryNotation}")
            }
            appendLine("        }")
            appendLine("")
        }
        val iosDeps = otherDeps.filter { it.platforms.contains(ProjectPlatform.Ios) }
        if (info.hasPlatform(ProjectPlatform.Ios) && iosDeps.isNotEmpty()) {
            appendLine("        iosMain.dependencies {")
            iosDeps.forEach { dep ->
                appendLine("            ${dep.libraryNotation}")
            }
            appendLine("        }")
            appendLine("")
        }
        appendLine("    }")
        appendLine("")
        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("    targets")
            appendLine("        .withType<KotlinNativeTarget>()")
            appendLine("        .matching { it.konanTarget.family.isAppleFamily }")
            appendLine("        .configureEach {")
            appendLine("            binaries {")
            appendLine("                framework {")
            appendLine("                    baseName = \"${info.frameworkName}\"")
            appendLine("                    isStatic = true")
            appendLine("                }")
            appendLine("            }")
            appendLine("        }")
        }
        appendLine("}")

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("")
            if (info.dependencies.contains(ComposeUiTooling)) {
                appendLine("dependencies {")
                appendLine("    debugImplementation(libs.${ComposeUiTooling.catalogAccessor})")
                appendLine("}")
            }
            appendLine("android {")
            appendLine("    namespace = \"${info.packageId}\"")
            appendLine("    compileSdk = ${info.androidTargetSdk}")
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

    private fun Dependency.useApiInsteadOfImplementationForComposeDeps() =
        if (this in DefaultComposeLibraries) "api(libs.$catalogAccessor)"
        else libraryNotation
}
