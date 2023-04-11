package wizard

import wizard.files.GradleLibsVersion
import wizard.files.app.ModuleBuildGradleKts
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = ProjectInfo(
            dependencies = allDependencies
        )
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/company/app/Color.kt
            composeApp/src/commonMain/kotlin/org/company/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/company/app/App.kt
            composeApp/src/commonMain/graphql/schema.graphqls
            composeApp/src/commonMain/graphql/HelloQuery.graphql
            composeApp/src/androidMain/AndroidManifest.xml
            composeApp/src/androidMain/kotlin/org/company/app/App.android.kt
            composeApp/src/desktopMain/kotlin/org/company/app/App.jvm.kt
            composeApp/src/desktopMain/kotlin/main.kt
            composeApp/composeApp.podspec
            composeApp/src/iosMain/kotlin/org/company/app/App.ios.kt
            composeApp/src/iosMain/kotlin/main.kt
            iosApp/Podfile
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json
            iosApp/iosApp/Assets.xcassets/AccentColor.colorset/Contents.json
            iosApp/iosApp/Assets.xcassets/Contents.json
            iosApp/iosApp/Preview Content/Preview Assets.xcassets/Contents.json
            iosApp/iosApp/iosApp.swift
            iosApp/iosApp.xcodeproj/project.xcworkspace/contents.xcworkspacedata
            iosApp/iosApp.xcodeproj/project.pbxproj
            composeApp/src/jsMain/kotlin/org/company/app/App.js.kt
            composeApp/src/jsMain/resources/index.html
            composeApp/src/jsMain/kotlin/main.kt
            composeApp/src/jsMain/kotlin/BrowserViewportWindow.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.cocoapods)
                    alias(libs.plugins.android.application)
                    alias(libs.plugins.apollo)
                    alias(libs.plugins.libres)
                    alias(libs.plugins.kotlinx.serialization)
                    alias(libs.plugins.sqlDelight)
                    alias(libs.plugins.buildConfig)
                }

                kotlin {
                    android {
                        compilations.all {
                            kotlinOptions {
                                jvmTarget = "1.8"
                            }
                        }
                    }

                    jvm("desktop")

                    js {
                        browser()
                        binaries.executable()
                    }

                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()

                    cocoapods {
                        version = "1.0.0"
                        summary = "Compose application framework"
                        homepage = "empty"
                        ios.deploymentTarget = "11.0"
                        podfile = project.file("../iosApp/Podfile")
                        framework {
                            baseName = "ComposeApp"
                            isStatic = true
                        }
                    }

                    sourceSets {
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
                                implementation(libs.apollo.runtime)
                                implementation(libs.libres)
                                implementation(libs.voyager.navigator)
                                implementation(libs.composeImageLoader)
                                implementation(libs.napier)
                                implementation(libs.kotlinx.datetime)
                                implementation(libs.multiplatformSettings)
                                implementation(libs.koin.core)
                                implementation(libs.kstore)
                                implementation(libs.composeIcons.featherIcons)
                                implementation(libs.ktor.core)
                                implementation(libs.kotlinx.coroutines.core)
                                implementation(libs.kotlinx.serialization.json)
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val androidMain by getting {
                            dependencies {
                                implementation(libs.androidx.appcompat)
                                implementation(libs.androidx.activityCompose)
                                implementation(libs.compose.uitooling)
                                implementation(libs.ktor.client.okhttp)
                                implementation(libs.kotlinx.coroutines.android)
                                implementation(libs.sqlDelight.driver.android)
                            }
                        }

                        val desktopMain by getting {
                            dependencies {
                                implementation(compose.desktop.common)
                                implementation(compose.desktop.currentOs)
                                implementation(libs.ktor.client.okhttp)
                                implementation(libs.sqlDelight.driver.sqlite)
                            }
                        }

                        val jsMain by getting {
                            dependencies {
                                implementation(compose.web.core)
                                implementation(libs.ktor.client.js)
                                implementation(libs.sqlDelight.driver.sqljs)
                            }
                        }

                        val iosX64Main by getting
                        val iosArm64Main by getting
                        val iosSimulatorArm64Main by getting
                        val iosMain by creating {
                            dependsOn(commonMain)
                            iosX64Main.dependsOn(this)
                            iosArm64Main.dependsOn(this)
                            iosSimulatorArm64Main.dependsOn(this)
                            dependencies {
                                implementation(libs.ktor.client.darwin)
                                implementation(libs.sqlDelight.driver.native)
                            }
                        }

                        val iosX64Test by getting
                        val iosArm64Test by getting
                        val iosSimulatorArm64Test by getting
                        val iosTest by creating {
                            dependsOn(commonTest)
                            iosX64Test.dependsOn(this)
                            iosArm64Test.dependsOn(this)
                            iosSimulatorArm64Test.dependsOn(this)
                        }
                    }
                }

                android {
                    namespace = "org.company.app"
                    compileSdk = 33

                    defaultConfig {
                        minSdk = 21
                        targetSdk = 33

                        applicationId = "org.company.app.androidApp"
                        versionCode = 1
                        versionName = "1.0.0"
                    }
                    sourceSets["main"].apply {
                        manifest.srcFile("src/androidMain/AndroidManifest.xml")
                        res.srcDirs("src/androidMain/resources")
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                    packagingOptions {
                        resources.excludes.add("META-INF/**")
                    }
                }

                compose.desktop {
                    application {
                        mainClass = "MainKt"

                        nativeDistributions {
                            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                            packageName = "org.company.app.desktopApp"
                            packageVersion = "1.0.0"
                        }
                    }
                }

                compose.experimental {
                    web.application {}
                }

                libres {
                    // https://github.com/Skeptick/libres#setup
                }
                tasks.getByPath("desktopProcessResources").dependsOn("libresGenerateResources")
                tasks.getByPath("desktopSourcesJar").dependsOn("libresGenerateResources")
                tasks.getByPath("jsProcessResources").dependsOn("libresGenerateResources")

                buildConfig {
                  // BuildConfig configuration here.
                  // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
                }

                sqldelight {
                  databases {
                    create("MyDatabase") {
                      // Database configuration here.
                      // https://cashapp.github.io/sqldelight
                      packageName.set("org.company.app.db")
                    }
                  }
                }

                apollo {
                  service("api") {
                    // GraphQL configuration here.
                    // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/
                    packageName.set("org.company.app.graphql")
                  }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            """
                [versions]

                kotlin = "${info.kotlinVersion}"
                agp = "${info.agpVersion}"
                compose = "${info.composeVersion}"
                androidx-appcompat = "${AndroidxAppcompat.version}"
                androidx-activityCompose = "${AndroidxActivityCompose.version}"
                apollo = "${ApolloRuntime.version}"
                compose-uitooling = "${ComposeUiTooling.version}"
                libres = "${LibresCompose.version}"
                voyager = "${Voyager.version}"
                composeImageLoader = "${ImageLoader.version}"
                napier = "${Napier.version}"
                kotlinx-datetime = "${KotlinxDateTime.version}"
                multiplatformSettings = "${MultiplatformSettings.version}"
                koin = "${Koin.version}"
                kstore = "${KStore.version}"
                composeIcons = "${ComposeIconsFeather.version}"
                ktor = "${KtorCore.version}"
                kotlinx-coroutines = "${KotlinxCoroutinesCore.version}"
                kotlinx-serialization = "${KotlinxSerializationJson.version}"
                sqlDelight = "${SQLDelightPlugin.version}"
                buildConfig = "${BuildConfigPlugin.version}"
                
                [libraries]

                androidx-appcompat = { module = "androidx.appcompat:appcompat", version.ref = "androidx-appcompat" }
                androidx-activityCompose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }
                apollo-runtime = { module = "com.apollographql.apollo3:apollo-runtime", version.ref = "apollo" }
                compose-uitooling = { module = "androidx.compose.ui:ui-tooling", version.ref = "compose-uitooling" }
                libres = { module = "io.github.skeptick.libres:libres-compose", version.ref = "libres" }
                voyager-navigator = { module = "cafe.adriel.voyager:voyager-navigator", version.ref = "voyager" }
                composeImageLoader = { module = "io.github.qdsfdhvh:image-loader", version.ref = "composeImageLoader" }
                napier = { module = "io.github.aakira:napier", version.ref = "napier" }
                kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "kotlinx-datetime" }
                multiplatformSettings = { module = "com.russhwolf:multiplatform-settings", version.ref = "multiplatformSettings" }
                koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
                kstore = { module = "io.github.xxfast:kstore", version.ref = "kstore" }
                composeIcons-featherIcons = { module = "br.com.devsrsouza.compose.icons:feather", version.ref = "composeIcons" }
                ktor-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
                ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
                ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
                ktor-client-js = { module = "io.ktor:ktor-client-js", version.ref = "ktor" }
                kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
                kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinx-coroutines" }
                kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
                sqlDelight-driver-sqlite = { module = "app.cash.sqldelight:sqlite-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-android = { module = "app.cash.sqldelight:android-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-native = { module = "app.cash.sqldelight:native-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-sqljs = { module = "app.cash.sqldelight:sqljs-driver", version.ref = "sqlDelight" }

                [plugins]

                multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                cocoapods = { id = "org.jetbrains.kotlin.native.cocoapods", version.ref = "kotlin" }
                compose = { id = "org.jetbrains.compose", version.ref = "compose" }
                android-application = { id = "com.android.application", version.ref = "agp" }
                apollo = { id = "com.apollographql.apollo3", version.ref = "apollo" }
                libres = { id = "io.github.skeptick.libres", version.ref = "libres" }
                kotlinx-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
                sqlDelight = { id = "app.cash.sqldelight", version.ref = "sqlDelight" }
                buildConfig = { id = "com.github.gmazzo.buildconfig", version.ref = "buildConfig" }

            """.trimIndent(),
            files.first { it is GradleLibsVersion }.content
        )
    }

    @Test
    fun buildAndroidFiles() {
        val info = ProjectInfo(
            packageId = "org.android.app",
            platforms = setOf(ComposePlatform.Android),
            dependencies = requiredAndroidDependencies + setOf(LibresPlugin, LibresCompose)
        )
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/android/app/Color.kt
            composeApp/src/commonMain/kotlin/org/android/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/android/app/App.kt
            composeApp/src/androidMain/AndroidManifest.xml
            composeApp/src/androidMain/kotlin/org/android/app/App.android.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                    alias(libs.plugins.libres)
                }

                kotlin {
                    android {
                        compilations.all {
                            kotlinOptions {
                                jvmTarget = "1.8"
                            }
                        }
                    }

                    sourceSets {
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
                                implementation(libs.libres)
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val androidMain by getting {
                            dependencies {
                                implementation(libs.androidx.appcompat)
                                implementation(libs.androidx.activityCompose)
                                implementation(libs.compose.uitooling)
                            }
                        }

                    }
                }

                android {
                    namespace = "org.android.app"
                    compileSdk = 33

                    defaultConfig {
                        minSdk = 21
                        targetSdk = 33

                        applicationId = "org.android.app.androidApp"
                        versionCode = 1
                        versionName = "1.0.0"
                    }
                    sourceSets["main"].apply {
                        manifest.srcFile("src/androidMain/AndroidManifest.xml")
                        res.srcDirs("src/androidMain/resources")
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                    packagingOptions {
                        resources.excludes.add("META-INF/**")
                    }
                }


                libres {
                    // https://github.com/Skeptick/libres#setup
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildIosFiles() {
        val info = ProjectInfo(
            packageId = "org.ios.app",
            platforms = setOf(ComposePlatform.Ios)
        )
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/ios/app/Color.kt
            composeApp/src/commonMain/kotlin/org/ios/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/ios/app/App.kt
            composeApp/composeApp.podspec
            composeApp/src/iosMain/kotlin/org/ios/app/App.ios.kt
            composeApp/src/iosMain/kotlin/main.kt
            iosApp/Podfile
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json
            iosApp/iosApp/Assets.xcassets/AccentColor.colorset/Contents.json
            iosApp/iosApp/Assets.xcassets/Contents.json
            iosApp/iosApp/Preview Content/Preview Assets.xcassets/Contents.json
            iosApp/iosApp/iosApp.swift
            iosApp/iosApp.xcodeproj/project.xcworkspace/contents.xcworkspacedata
            iosApp/iosApp.xcodeproj/project.pbxproj
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.cocoapods)
                }

                kotlin {
                    iosX64()
                    iosArm64()
                    iosSimulatorArm64()

                    cocoapods {
                        version = "1.0.0"
                        summary = "Compose application framework"
                        homepage = "empty"
                        ios.deploymentTarget = "11.0"
                        podfile = project.file("../iosApp/Podfile")
                        framework {
                            baseName = "ComposeApp"
                            isStatic = true
                        }
                        extraSpecAttributes["resources"] = "['src/commonMain/resources/**']"
                    }

                    sourceSets {
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
                                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                                implementation(compose.components.resources)
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val iosX64Main by getting
                        val iosArm64Main by getting
                        val iosSimulatorArm64Main by getting
                        val iosMain by creating {
                            dependsOn(commonMain)
                            iosX64Main.dependsOn(this)
                            iosArm64Main.dependsOn(this)
                            iosSimulatorArm64Main.dependsOn(this)
                            dependencies {
                            }
                        }

                        val iosX64Test by getting
                        val iosArm64Test by getting
                        val iosSimulatorArm64Test by getting
                        val iosTest by creating {
                            dependsOn(commonTest)
                            iosX64Test.dependsOn(this)
                            iosArm64Test.dependsOn(this)
                            iosSimulatorArm64Test.dependsOn(this)
                        }
                    }
                }


            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildDesktopFiles() {
        val info = ProjectInfo(
            packageId = "org.desktop.app",
            platforms = setOf(ComposePlatform.Desktop)
        )
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/desktop/app/Color.kt
            composeApp/src/commonMain/kotlin/org/desktop/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/desktop/app/App.kt
            composeApp/src/desktopMain/kotlin/org/desktop/app/App.jvm.kt
            composeApp/src/desktopMain/kotlin/main.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                }

                kotlin {
                    jvm("desktop")

                    sourceSets {
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
                                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                                implementation(compose.components.resources)
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val desktopMain by getting {
                            dependencies {
                                implementation(compose.desktop.common)
                                implementation(compose.desktop.currentOs)
                            }
                        }

                    }
                }

                compose.desktop {
                    application {
                        mainClass = "MainKt"

                        nativeDistributions {
                            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                            packageName = "org.desktop.app.desktopApp"
                            packageVersion = "1.0.0"
                        }
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildBrowserFiles() {
        val info = ProjectInfo(
            packageId = "org.js.app",
            platforms = setOf(ComposePlatform.Browser)
        )
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/js/app/Color.kt
            composeApp/src/commonMain/kotlin/org/js/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/js/app/App.kt
            composeApp/src/jsMain/kotlin/org/js/app/App.js.kt
            composeApp/src/jsMain/resources/index.html
            composeApp/src/jsMain/kotlin/main.kt
            composeApp/src/jsMain/kotlin/BrowserViewportWindow.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                }

                kotlin {
                    js {
                        browser()
                        binaries.executable()
                    }

                    sourceSets {
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
                                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                                implementation(compose.components.resources)
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val jsMain by getting {
                            dependencies {
                                implementation(compose.web.core)
                            }
                        }

                    }
                }


                compose.experimental {
                    web.application {}
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }
}
