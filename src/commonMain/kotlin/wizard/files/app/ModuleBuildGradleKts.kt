package wizard.files.app

import wizard.*

class ModuleBuildGradleKts(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/build.gradle.kts"
    override val content = buildString {
        val plugins = mutableSetOf<Dependency>()
        val commonDeps = mutableSetOf<Dependency>()
        val otherDeps = mutableSetOf<Dependency>()
        info.dependencies.forEach { dep ->
            when  {
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
        appendLine("    kotlin(\"multiplatform\")")
        appendLine("    id(\"org.jetbrains.compose\")")
        if (info.hasIos) {
            appendLine("    kotlin(\"native.cocoapods\")")
        }
        if (info.hasAndroid) {
            appendLine("    id(\"com.android.application\")")
        }
        plugins.forEach { dep ->
            appendLine("    ${dep.pluginNotation()}")
        }
        appendLine("}")
        appendLine("")
        appendLine("kotlin {")
        if (info.hasAndroid) {
            appendLine("    android()")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("    jvm(\"desktop\")")
            appendLine("")
        }
        if (info.hasIos) {
            appendLine("    iosX64()")
            appendLine("    iosArm64()")
            appendLine("    iosSimulatorArm64()")
            appendLine("")
            appendLine("    cocoapods {")
            appendLine("        version = \"1.0.0\"")
            appendLine("        summary = \"Compose application framework\"")
            appendLine("        homepage = \"empty\"")
            appendLine("        podfile = project.file(\"../iosApp/Podfile\")")
            appendLine("        framework {")
            appendLine("            baseName = \"ComposeApp\"")
            appendLine("            isStatic = true")
            appendLine("        }")
            appendLine("    }")
            appendLine("")
        }
        appendLine("    sourceSets {")
        appendLine("        val commonMain by getting {")
        appendLine("            dependencies {")
        appendLine("                implementation(compose.runtime)")
        appendLine("                implementation(compose.foundation)")
        appendLine("                implementation(compose.material)")

        commonDeps.forEach { dep ->
            appendLine("                ${dep.libraryNotation()}")
        }

        appendLine("            }")
        appendLine("        }")
        appendLine("")
        appendLine("        val commonTest by getting {")
        appendLine("            dependencies {")
        appendLine("                implementation(kotlin(\"test\"))")
        appendLine("            }")
        appendLine("        }")
        appendLine("")
        if (info.hasAndroid) {
            appendLine("        val androidMain by getting {")
            appendLine("            dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Android)) {
                    appendLine("                ${dep.libraryNotation()}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasDesktop) {
            appendLine("        val desktopMain by getting {")
            appendLine("            dependencies {")
            appendLine("                implementation(compose.desktop.common)")
            appendLine("                implementation(compose.desktop.currentOs)")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Desktop)) {
                    appendLine("                ${dep.libraryNotation()}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
        }
        if (info.hasIos) {
            appendLine("        val iosX64Main by getting")
            appendLine("        val iosArm64Main by getting")
            appendLine("        val iosSimulatorArm64Main by getting")
            appendLine("        val iosMain by creating {")
            appendLine("            dependsOn(commonMain)")
            appendLine("            iosX64Main.dependsOn(this)")
            appendLine("            iosArm64Main.dependsOn(this)")
            appendLine("            iosSimulatorArm64Main.dependsOn(this)")
            appendLine("            dependencies {")

            otherDeps.forEach { dep ->
                if (dep.platforms.contains(ComposePlatform.Ios)) {
                    appendLine("                ${dep.libraryNotation()}")
                }
            }

            appendLine("            }")
            appendLine("        }")
            appendLine("")
            appendLine("        val iosX64Test by getting")
            appendLine("        val iosArm64Test by getting")
            appendLine("        val iosSimulatorArm64Test by getting")
            appendLine("        val iosTest by creating {")
            appendLine("            dependsOn(commonTest)")
            appendLine("            iosX64Test.dependsOn(this)")
            appendLine("            iosArm64Test.dependsOn(this)")
            appendLine("            iosSimulatorArm64Test.dependsOn(this)")
            appendLine("        }")
        }
        appendLine("    }")
        appendLine("}")
        appendLine("")
        if (info.hasAndroid) {
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
            appendLine("    }")
            appendLine("    kotlin {")
            appendLine("        jvmToolchain(8)")
            appendLine("    }")
            appendLine("    compileOptions {")
            appendLine("        sourceCompatibility = JavaVersion.VERSION_1_8")
            appendLine("        targetCompatibility = JavaVersion.VERSION_1_8")
            appendLine("    }")
            appendLine("    packagingOptions {")
            appendLine("        resources.excludes.add(\"META-INF/**\")")
            appendLine("    }")
            appendLine("}")
            appendLine("")
        }
        if (info.hasDesktop) {
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

            if (plugins.contains(LibresPlugin)) {
                appendLine("tasks.getByPath(\"desktopProcessResources\").dependsOn(\"libresGenerateResources\")")
            }
        }

        if (plugins.contains(LibresPlugin)) {
            appendLine("")
            appendLine("libres {")
            appendLine("    // https://github.com/Skeptick/libres#setup")
            appendLine("}")
        }

        if (plugins.contains(SQLDelightPlugin)) {
            appendLine("")
            appendLine("sqldelight {")
            appendLine("  databases {")
            appendLine("    create(\"MyDatabase\") {")
            appendLine("      // Database configuration here.")
            appendLine("      // https://cashapp.github.io/sqldelight")
            appendLine("    }")
            appendLine("  }")
            appendLine("}")
        }
    }
}