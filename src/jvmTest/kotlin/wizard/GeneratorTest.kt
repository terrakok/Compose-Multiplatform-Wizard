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
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/company/app/theme/Color.kt
            composeApp/src/commonMain/kotlin/org/company/app/theme/Theme.kt
            composeApp/src/commonMain/kotlin/org/company/app/App.kt
            composeApp/src/commonMain/graphql/schema.graphqls
            composeApp/src/commonMain/graphql/HelloQuery.graphql
            composeApp/src/androidMain/AndroidManifest.xml
            composeApp/src/androidMain/kotlin/org/company/app/App.android.kt
            composeApp/src/androidMain/kotlin/org/company/app/theme/Theme.android.kt
            composeApp/src/jvmMain/kotlin/org/company/app/App.jvm.kt
            composeApp/src/jvmMain/kotlin/main.kt
            composeApp/src/jvmMain/kotlin/org/company/app/theme/Theme.jvm.kt
            composeApp/src/iosMain/kotlin/org/company/app/App.ios.kt
            composeApp/src/iosMain/kotlin/main.kt
            composeApp/src/iosMain/kotlin/org/company/app/theme/Theme.ios.kt
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
            composeApp/src/jsMain/kotlin/org/company/app/theme/Theme.js.kt
            composeApp/webpack.config.d/config.js
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                    alias(libs.plugins.apollo)
                    alias(libs.plugins.kotlinx.serialization)
                    alias(libs.plugins.sqlDelight)
                    alias(libs.plugins.buildConfig)
                }

                kotlin {
                    androidTarget {
                        compilations.all {
                            kotlinOptions {
                                jvmTarget = "17"
                            }
                        }
                    }

                    jvm()

                    js {
                        browser()
                        binaries.executable()
                    }

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
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.material3)
                            implementation(compose.materialIconsExtended)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.components.resources)
                            implementation(libs.apollo.runtime)
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
                            implementation(libs.moko.mvvm)
                            implementation(libs.kotlinx.serialization.json)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        androidMain.dependencies {
                            implementation(libs.androidx.appcompat)
                            implementation(libs.androidx.activityCompose)
                            implementation(libs.compose.uitooling)
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.kotlinx.coroutines.android)
                            implementation(libs.sqlDelight.driver.android)
                        }

                        jvmMain.dependencies {
                            implementation(compose.desktop.common)
                            implementation(compose.desktop.currentOs)
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.sqlDelight.driver.sqlite)
                        }

                        jsMain.dependencies {
                            implementation(compose.html.core)
                            implementation(libs.ktor.client.js)
                            implementation(libs.sqlDelight.driver.js)
                        }

                        iosMain.dependencies {
                            implementation(libs.ktor.client.darwin)
                            implementation(libs.sqlDelight.driver.native)
                        }

                    }
                }

                android {
                    namespace = "org.company.app"
                    compileSdk = ${info.androidTargetSdk}

                    defaultConfig {
                        minSdk = ${info.androidMinSdk}
                        targetSdk = ${info.androidTargetSdk}

                        applicationId = "org.company.app.androidApp"
                        versionCode = 1
                        versionName = "1.0.0"
                    }
                    sourceSets["main"].apply {
                        manifest.srcFile("src/androidMain/AndroidManifest.xml")
                        res.srcDirs("src/androidMain/resources")
                        resources.srcDirs("src/commonMain/resources")
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                    buildFeatures {
                        compose = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion = "${info.composeCompilerVersion}"
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
                moko-mvvm = "${MokoMvvm.version}"
                kotlinx-serialization = "${KotlinxSerializationJson.version}"
                sqlDelight = "${SQLDelightPlugin.version}"
                buildConfig = "${BuildConfigPlugin.version}"
                
                [libraries]

                androidx-appcompat = { module = "androidx.appcompat:appcompat", version.ref = "androidx-appcompat" }
                androidx-activityCompose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }
                apollo-runtime = { module = "com.apollographql.apollo3:apollo-runtime", version.ref = "apollo" }
                compose-uitooling = { module = "androidx.compose.ui:ui-tooling", version.ref = "compose-uitooling" }
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
                moko-mvvm = { module = "dev.icerock.moko:mvvm-compose", version.ref = "moko-mvvm" }
                kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
                sqlDelight-driver-sqlite = { module = "app.cash.sqldelight:sqlite-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-android = { module = "app.cash.sqldelight:android-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-native = { module = "app.cash.sqldelight:native-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-js = { module = "app.cash.sqldelight:web-worker-driver", version.ref = "sqlDelight" }

                [plugins]

                multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                compose = { id = "org.jetbrains.compose", version.ref = "compose" }
                android-application = { id = "com.android.application", version.ref = "agp" }
                apollo = { id = "com.apollographql.apollo3", version.ref = "apollo" }
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
            dependencies = requiredAndroidDependencies
        )
        val files = info.buildFiles()

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
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/android/app/theme/Color.kt
            composeApp/src/commonMain/kotlin/org/android/app/theme/Theme.kt
            composeApp/src/commonMain/kotlin/org/android/app/App.kt
            composeApp/src/androidMain/AndroidManifest.xml
            composeApp/src/androidMain/kotlin/org/android/app/App.android.kt
            composeApp/src/androidMain/kotlin/org/android/app/theme/Theme.android.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                }

                kotlin {
                    androidTarget {
                        compilations.all {
                            kotlinOptions {
                                jvmTarget = "17"
                            }
                        }
                    }

                    sourceSets {
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.material3)
                            implementation(compose.materialIconsExtended)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.components.resources)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        androidMain.dependencies {
                            implementation(libs.androidx.appcompat)
                            implementation(libs.androidx.activityCompose)
                            implementation(libs.compose.uitooling)
                        }

                    }
                }

                android {
                    namespace = "org.android.app"
                    compileSdk = ${info.androidTargetSdk}

                    defaultConfig {
                        minSdk = ${info.androidMinSdk}
                        targetSdk = ${info.androidTargetSdk}

                        applicationId = "org.android.app.androidApp"
                        versionCode = 1
                        versionName = "1.0.0"
                    }
                    sourceSets["main"].apply {
                        manifest.srcFile("src/androidMain/AndroidManifest.xml")
                        res.srcDirs("src/androidMain/resources")
                        resources.srcDirs("src/commonMain/resources")
                    }
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }
                    buildFeatures {
                        compose = true
                    }
                    composeOptions {
                        kotlinCompilerExtensionVersion = "${info.composeCompilerVersion}"
                    }
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
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/ios/app/theme/Color.kt
            composeApp/src/commonMain/kotlin/org/ios/app/theme/Theme.kt
            composeApp/src/commonMain/kotlin/org/ios/app/App.kt
            composeApp/src/iosMain/kotlin/org/ios/app/App.ios.kt
            composeApp/src/iosMain/kotlin/main.kt
            composeApp/src/iosMain/kotlin/org/ios/app/theme/Theme.ios.kt
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
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.material3)
                            implementation(compose.materialIconsExtended)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.components.resources)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        iosMain.dependencies {
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
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/desktop/app/theme/Color.kt
            composeApp/src/commonMain/kotlin/org/desktop/app/theme/Theme.kt
            composeApp/src/commonMain/kotlin/org/desktop/app/App.kt
            composeApp/src/jvmMain/kotlin/org/desktop/app/App.jvm.kt
            composeApp/src/jvmMain/kotlin/main.kt
            composeApp/src/jvmMain/kotlin/org/desktop/app/theme/Theme.jvm.kt
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
                    jvm()

                    sourceSets {
                        all {
                            languageSettings {
                                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                            }
                        }
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.material3)
                            implementation(compose.materialIconsExtended)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.components.resources)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        jvmMain.dependencies {
                            implementation(compose.desktop.common)
                            implementation(compose.desktop.currentOs)
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
            gradlew.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle/libs.versions.toml
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/js/app/theme/Color.kt
            composeApp/src/commonMain/kotlin/org/js/app/theme/Theme.kt
            composeApp/src/commonMain/kotlin/org/js/app/App.kt
            composeApp/src/jsMain/kotlin/org/js/app/App.js.kt
            composeApp/src/jsMain/resources/index.html
            composeApp/src/jsMain/kotlin/main.kt
            composeApp/src/jsMain/kotlin/org/js/app/theme/Theme.js.kt
            composeApp/webpack.config.d/config.js
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
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.material3)
                            implementation(compose.materialIconsExtended)
                            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                            implementation(compose.components.resources)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                        }

                        jsMain.dependencies {
                            implementation(compose.html.core)
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
