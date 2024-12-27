package wizard

import wizard.ProjectPlatform.*
import wizard.dependencies.*
import wizard.files.GradleLibsVersion
import wizard.files.kmpLibrary.Readme
import wizard.files.kmpLibrary.ModuleBuildGradleKts
import wizard.files.kmpLibrary.sample.SampleComposeAppBuildGradleKts
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            platforms = setOf(Android, Ios, Jvm, Js, Macos, Linux, Mingw, Wasm),
            dependencies =  buildSet {
                add(KotlinPlugin)
                addAll(kmpLibraryExtraDependencies)
            }
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            convention-plugins/build.gradle.kts
            convention-plugins/src/main/kotlin/convention.publication.gradle.kts
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/my/company/name/Fibonacci.kt
            ${info.moduleName}/src/commonTest/kotlin/my/company/name/FibonacciTest.kt
            sample/composeApp/build.gradle.kts
            sample/composeApp/src/commonMain/kotlin/sample/app/App.kt
            sample/composeApp/src/androidMain/kotlin/sample/app/main.kt
            sample/composeApp/src/androidMain/AndroidManifest.xml
            sample/composeApp/src/iosMain/kotlin/sample/app/main.kt
            sample/iosApp/iosApp/Info.plist
            sample/iosApp/iosApp/iosApp.swift
            sample/iosApp/iosApp.xcodeproj/project.pbxproj
            sample/composeApp/src/jvmMain/kotlin/sample/app/main.kt
            sample/composeApp/src/jsMain/kotlin/sample/app/main.kt
            sample/composeApp/src/jsMain/resources/index.html
            sample/composeApp/src/wasmJsMain/kotlin/sample/app/main.kt
            sample/composeApp/src/wasmJsMain/resources/index.html
            sample/terminalApp/build.gradle.kts
            sample/terminalApp/src/commonMain/kotlin/main.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.kotlinx.serialization)
                    alias(libs.plugins.sqlDelight)
                    alias(libs.plugins.buildConfig)
                    id("convention.publication")
                }

                group = "my.company.name"
                version = "1.0.0"

                kotlin {
                    jvmToolchain(17)

                    androidTarget { publishLibraryVariants("release") }
                    jvm()
                    js { browser() }
                    wasmJs { browser() }
                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()
                    macosX64()
                    macosArm64()
                    linuxX64()
                    mingwX64()

                    sourceSets {
                        commonMain.dependencies {
                            implementation(libs.kotlinx.datetime)
                            implementation(libs.multiplatformSettings)
                            implementation(libs.koin.core)
                            implementation(libs.kermit)
                            implementation(libs.kstore)
                            implementation(libs.ktor.client.core)
                            implementation(libs.kotlinx.coroutines.core)
                            implementation(libs.kotlinx.serialization.json)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        androidMain.dependencies {
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.kotlinx.coroutines.android)
                            implementation(libs.sqlDelight.driver.android)
                        }

                        jvmMain.dependencies {
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.sqlDelight.driver.sqlite)
                        }

                        jsMain.dependencies {
                            implementation(libs.ktor.client.js)
                            implementation(libs.sqlDelight.driver.js)
                        }

                        iosMain.dependencies {
                            implementation(libs.ktor.client.darwin)
                            implementation(libs.sqlDelight.driver.native)
                        }

                        macosMain.dependencies {
                            implementation(libs.ktor.client.darwin)
                            implementation(libs.sqlDelight.driver.native)
                        }

                        linuxMain.dependencies {
                            implementation(libs.ktor.client.curl)
                            implementation(libs.sqlDelight.driver.native)
                        }

                        mingwMain.dependencies {
                            implementation(libs.ktor.client.winhttp)
                            implementation(libs.sqlDelight.driver.native)
                        }

                    }

                    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
                    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
                        compilations["main"].compileTaskProvider.configure {
                            compilerOptions {
                                freeCompilerArgs.add("-Xexport-kdoc")
                            }
                        }
                    }

                }

                android {
                    namespace = "my.company.name"
                    compileSdk = ${info.androidTargetSdk}

                    defaultConfig {
                        minSdk = ${info.androidMinSdk}
                    }
                }

                buildConfig {
                    // BuildConfig configuration here.
                    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
                }

                sqldelight {
                    databases {
                        create("MyDatabase") {
                            // Database configuration here.
                            // https://cashapp.github.io/sqldelight
                            packageName.set("my.company.name.db")
                        }
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            """
                [versions]
                
                kotlin = "${KotlinPlugin.version}"
                kotlinx-datetime = "${KotlinxDateTime.version}"
                multiplatformSettings = "${MultiplatformSettings.version}"
                koin = "${Koin.version}"
                kermit = "${Kermit.version}"
                kstore = "${KStore.version}"
                ktor = "${KtorCore.version}"
                kotlinx-coroutines = "${KotlinxCoroutinesCore.version}"
                kotlinx-serialization = "${KotlinxSerializationJson.version}"
                sqlDelight = "${SQLDelightPlugin.version}"
                buildConfig = "${BuildConfigPlugin.version}"
                compose = "${ComposePlugin.version}"
                agp = "${AndroidLibraryPlugin.version}"
                androidx-activityCompose = "${AndroidxActivityCompose.version}"

                [libraries]

                kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "kotlinx-datetime" }
                multiplatformSettings = { module = "com.russhwolf:multiplatform-settings", version.ref = "multiplatformSettings" }
                koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
                kermit = { module = "co.touchlab:kermit", version.ref = "kermit" }
                kstore = { module = "io.github.xxfast:kstore", version.ref = "kstore" }
                ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
                ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
                ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
                ktor-client-js = { module = "io.ktor:ktor-client-js", version.ref = "ktor" }
                ktor-client-curl = { module = "io.ktor:ktor-client-curl", version.ref = "ktor" }
                ktor-client-winhttp = { module = "io.ktor:ktor-client-winhttp", version.ref = "ktor" }
                kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
                kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinx-coroutines" }
                kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
                sqlDelight-driver-sqlite = { module = "app.cash.sqldelight:sqlite-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-android = { module = "app.cash.sqldelight:android-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-native = { module = "app.cash.sqldelight:native-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-js = { module = "app.cash.sqldelight:web-worker-driver", version.ref = "sqlDelight" }
                androidx-activityCompose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }

                [plugins]

                multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                kotlinx-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
                sqlDelight = { id = "app.cash.sqldelight", version.ref = "sqlDelight" }
                buildConfig = { id = "com.github.gmazzo.buildconfig", version.ref = "buildConfig" }
                compose = { id = "org.jetbrains.compose", version.ref = "compose" }
                compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
                android-application = { id = "com.android.application", version.ref = "agp" }

            """.trimIndent(),
            files.first { it is GradleLibsVersion }.content
        )
        assertEquals(
            """
                # KMP library

                Kotlin Multiplatform Library

                ### Run Sample App

                 - Desktop JVM: `./gradlew :sample:composeApp:run`
                 - Android: `open project in Android Studio and run the sample app`
                 - iOS: `open 'sample/iosApp/iosApp.xcodeproj' in Xcode and run the sample app`
                 - JavaScript: `./gradlew :sample:composeApp:jsBrowserRun`
                 - Wasm: `./gradlew :sample:composeApp:wasmJsBrowserRun`
                 - Linux/Macos/Windows native: `./gradlew :sample:terminalApp:runDebugExecutable[architecture]`

                ### Publish to MavenLocal

                1) Run `./gradlew :shared:publishToMavenLocal`
                2) Open `~/.m2/repository/my/company/name/"}`

                ### Publish to MavenCentral

                1) Create a account and a namespace on Sonatype:
                   https://central.sonatype.org/register/central-portal/#create-an-account
                2) Add developer id, name, email and the project url to
                   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
                3) Generate a GPG key:
                   https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/#generating-a-gpg-key-pair
                   ```
                   gpg --full-gen-key
                   gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXX
                   gpg --export-secret-key XXXXXXXX > XXXXXXXX.gpg
                   ```
                4) Add the secrets to `local.properties`:
                   ```
                   signing.keyId=XXXXXXXX
                   signing.password=[key password]
                   signing.secretKeyRingFile=../XXXXXXXX.gpg
                   ossrhUsername=[Sonatype token_user]
                   ossrhPassword=[Sonatype token_password]
                   ```
                5) Run `./gradlew :shared:publishAllPublicationsToSonatypeRepository`

            """.trimIndent(),
            files.first { it is Readme }.content
        )
    }

    @Test
    fun buildJvmFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "org.desktop.app",
            moduleName = "awesome",
            platforms = setOf(Jvm),
            dependencies = setOf(KotlinPlugin)
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            convention-plugins/build.gradle.kts
            convention-plugins/src/main/kotlin/convention.publication.gradle.kts
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/desktop/app/Fibonacci.kt
            ${info.moduleName}/src/commonTest/kotlin/org/desktop/app/FibonacciTest.kt
            sample/composeApp/build.gradle.kts
            sample/composeApp/src/commonMain/kotlin/sample/app/App.kt
            sample/composeApp/src/jvmMain/kotlin/sample/app/main.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    id("convention.publication")
                }

                group = "org.desktop.app"
                version = "1.0.0"

                kotlin {
                    jvm()

                    sourceSets {
                        commonMain.dependencies {
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                    }

                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildJvmAndAndroidFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "my.company",
            moduleName = "foo",
            platforms = setOf(Jvm, Android)
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            convention-plugins/build.gradle.kts
            convention-plugins/src/main/kotlin/convention.publication.gradle.kts
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/my/company/Fibonacci.kt
            ${info.moduleName}/src/commonTest/kotlin/my/company/FibonacciTest.kt
            sample/composeApp/build.gradle.kts
            sample/composeApp/src/commonMain/kotlin/sample/app/App.kt
            sample/composeApp/src/androidMain/kotlin/sample/app/main.kt
            sample/composeApp/src/androidMain/AndroidManifest.xml
            sample/composeApp/src/jvmMain/kotlin/sample/app/main.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.android.library)
                    id("convention.publication")
                }
                
                group = "my.company"
                version = "1.0.0"
                
                kotlin {
                    jvmToolchain(17)

                    androidTarget { publishLibraryVariants("release") }
                    jvm()

                    sourceSets {
                        commonMain.dependencies {
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                    }

                }
                
                android {
                    namespace = "my.company"
                    compileSdk = ${info.androidTargetSdk}

                    defaultConfig {
                        minSdk = ${info.androidMinSdk}
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            """
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose.compiler)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                }

                kotlin {
                    jvmToolchain(17)

                    androidTarget()
                    jvm()

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(project(":foo"))
                        }

                        androidMain.dependencies {
                            implementation(libs.androidx.activityCompose)
                        }

                        jvmMain.dependencies {
                            implementation(compose.desktop.currentOs)
                        }

                    }
                }

                android {
                    namespace = "sample.app"
                    compileSdk = 35

                    defaultConfig {
                        minSdk = 21
                        targetSdk = 35

                        applicationId = "sample.app.androidApp"
                        versionCode = 1
                        versionName = "1.0.0"
                    }
                }

                compose.desktop {
                    application {
                        mainClass = "MainKt"

                        nativeDistributions {
                            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                            packageName = "sample"
                            packageVersion = "1.0.0"
                        }
                    }
                }

            """.trimIndent(),
            files.first { it is SampleComposeAppBuildGradleKts }.content
        )
    }

    @Test
    fun buildNativeFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "io.my.com",
            moduleName = "lamba",
            platforms = setOf(Ios, Linux)
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            convention-plugins/build.gradle.kts
            convention-plugins/src/main/kotlin/convention.publication.gradle.kts
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/io/my/com/Fibonacci.kt
            ${info.moduleName}/src/commonTest/kotlin/io/my/com/FibonacciTest.kt
            sample/composeApp/build.gradle.kts
            sample/composeApp/src/commonMain/kotlin/sample/app/App.kt
            sample/composeApp/src/iosMain/kotlin/sample/app/main.kt
            sample/iosApp/iosApp/Info.plist
            sample/iosApp/iosApp/iosApp.swift
            sample/iosApp/iosApp.xcodeproj/project.pbxproj
            sample/terminalApp/build.gradle.kts
            sample/terminalApp/src/commonMain/kotlin/main.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.android.library)
                    id("convention.publication")
                }

                group = "io.my.com"
                version = "1.0.0"

                kotlin {
                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()
                    linuxX64()

                    sourceSets {
                        commonMain.dependencies {
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                    }

                    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
                    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
                        compilations["main"].compileTaskProvider.configure {
                            compilerOptions {
                                freeCompilerArgs.add("-Xexport-kdoc")
                            }
                        }
                    }

                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose.compiler)
                    alias(libs.plugins.compose)
                }

                kotlin {
                    listOf(
                        iosX64(),
                        iosArm64(),
                        iosSimulatorArm64()
                    ).forEach {
                        it.binaries.framework {
                            baseName = "ComposeApp"
                            isStatic = true
                        }
                    }

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(project(":${info.moduleName}"))
                        }

                    }
                }


            """.trimIndent(),
            files.first { it is SampleComposeAppBuildGradleKts }.content
        )
    }
}
