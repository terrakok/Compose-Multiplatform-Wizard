package wizard

import wizard.ProjectPlatform.*
import wizard.dependencies.*
import wizard.files.GradleLibsVersion
import wizard.files.kmpLibrary.Readme
import wizard.files.kmpLibrary.ModuleBuildGradleKts
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
                version = "1.0"

                kotlin {
                    jvmToolchain(11)
                    androidTarget {
                        publishLibraryVariants("release")
                    }

                    jvm()

                    js {
                        browser {
                            webpackTask {
                                mainOutputFileName = "shared.js"
                            }
                        }
                        binaries.executable()
                    }

                    wasmJs {
                        browser()
                        binaries.executable()
                    }

                    listOf(
                        iosX64(),
                        iosArm64(),
                        iosSimulatorArm64()
                    ).forEach {
                        it.binaries.framework {
                            baseName = "shared"
                            isStatic = true
                        }
                    }

                    listOf(
                        macosX64(),
                        macosArm64()
                    ).forEach {
                        it.binaries.framework {
                            baseName = "shared"
                            isStatic = true
                        }
                    }

                    linuxX64 {
                        binaries.staticLib {
                            baseName = "shared"
                        }
                    }


                    mingwX64 {
                        binaries.staticLib {
                            baseName = "shared"
                        }
                    }

                    sourceSets {
                        commonMain.dependencies {
                            implementation(libs.kotlinx.datetime)
                            implementation(libs.multiplatformSettings)
                            implementation(libs.koin.core)
                            implementation(libs.kermit)
                            implementation(libs.kstore)
                            implementation(libs.ktor.core)
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
                        compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
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

                [libraries]

                kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "kotlinx-datetime" }
                multiplatformSettings = { module = "com.russhwolf:multiplatform-settings", version.ref = "multiplatformSettings" }
                koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
                kermit = { module = "co.touchlab:kermit", version.ref = "kermit" }
                kstore = { module = "io.github.xxfast:kstore", version.ref = "kstore" }
                ktor-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
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

                [plugins]

                multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                kotlinx-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
                sqlDelight = { id = "app.cash.sqldelight", version.ref = "sqlDelight" }
                buildConfig = { id = "com.github.gmazzo.buildconfig", version.ref = "buildConfig" }

            """.trimIndent(),
            files.first { it is GradleLibsVersion }.content
        )
        assertEquals(
            """
                # KMP library

                Kotlin Multiplatform Library

                ### Publish to MavenCentral

                1) Registering a Sonatype account as described here: 
                   https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
                2) Add developer id, name, email and the project url to
                   `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
                3) Add the secrets to `local.properties`:

                ```
                signing.keyId=...
                signing.password=...
                signing.secretKeyRingFile=...
                ossrhUsername=...
                ossrhPassword=...
                ```

                4) Run `./gradlew :shared:publishAllPublicationsToSonatypeRepository`

                ### Build platform artifacts

                #### Android aar

                - Run `./gradlew :shared:assembleRelease`
                - Output: `/shared/build/outputs/aar/shared-release.aar`

                #### JVM jar

                - Run `./gradlew :shared:jvmJar`
                - Output: `/shared/build/libs/shared-jvm-1.0.jar`

                #### iOS Framework

                - Run `./gradlew :shared:linkReleaseFrameworkIosArm64`
                - Output: `/shared/build/bin/iosArm64/releaseFramework/shared.framework`

                #### JS file

                - Run `./gradlew :shared:jsBrowserProductionWebpack`
                - Output: `/shared/build/dist/js/productionExecutable/shared.js`

                #### macOS Framework

                - Run `./gradlew :shared:linkReleaseFrameworkMacosArm64`
                - Output: `/shared/build/bin/macosArm64/releaseFramework/shared.framework`

                #### Linux static library

                - Run `./gradlew :shared:linkReleaseStaticLinuxX64`
                - Output: `/shared/build/bin/linuxX64/releaseStatic/libshared.a`

                #### Windows static library

                - Run `./gradlew :shared:linkReleaseStaticMingwX64`
                - Output: `/shared/build/bin/mingwX64/releaseStatic/libshared.a`

                #### Wasm binary file

                - Run `./gradlew :shared:wasmJsBrowserDistribution`
                - Output: `/shared/build/dist/wasmJs/productionExecutable/shared-wasm-js.wasm`

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
                version = "1.0"

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
                version = "1.0"
                
                kotlin {
                    jvmToolchain(11)
                    androidTarget {
                        publishLibraryVariants("release")
                    }
                
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
    }
}
