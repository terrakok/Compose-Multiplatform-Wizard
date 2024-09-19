package wizard

import wizard.dependencies.*
import wizard.files.GradleLibsVersion
import wizard.files.composeApp.ModuleBuildGradleKts
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppGeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = DefaultComposeAppInfo().copy(
            dependencies =  buildSet {
                add(KotlinPlugin)
                add(ComposeCompilerPlugin)
                add(ComposePlugin)
                addAll(androidDependencies)
                addAll(extraDependencies)
                addAll(roomDependencies)
            }
        )
        val files = info.generateComposeAppFiles()

        val projectDir = "\$projectDir"
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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/company/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/company/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/company/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/company/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/commonMain/graphql/schema.graphqls
            ${info.moduleName}/src/commonMain/graphql/HelloQuery.graphql
            ${info.moduleName}/src/androidMain/AndroidManifest.xml
            ${info.moduleName}/src/androidMain/res/mipmap-anydpi-v26/ic_launcher.xml
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/kotlin/org/company/app/App.android.kt
            ${info.moduleName}/src/androidMain/kotlin/org/company/app/theme/Theme.android.kt
            ${info.moduleName}/src/jvmMain/kotlin/main.kt
            ${info.moduleName}/src/jvmMain/kotlin/org/company/app/theme/Theme.jvm.kt
            ${info.moduleName}/desktopAppIcons/LinuxIcon.png
            ${info.moduleName}/desktopAppIcons/WindowsIcon.ico
            ${info.moduleName}/desktopAppIcons/MacosIcon.icns
            ${info.moduleName}/src/iosMain/kotlin/main.kt
            ${info.moduleName}/src/iosMain/kotlin/org/company/app/theme/Theme.ios.kt
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-60@2x~car.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-60@3x~car.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-83.5@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon~ios-marketing.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json
            iosApp/iosApp/Assets.xcassets/AccentColor.colorset/Contents.json
            iosApp/iosApp/Assets.xcassets/Contents.json
            iosApp/iosApp/Preview Content/Preview Assets.xcassets/Contents.json
            iosApp/iosApp/iosApp.swift
            iosApp/iosApp.xcodeproj/project.xcworkspace/contents.xcworkspacedata
            iosApp/iosApp.xcodeproj/project.pbxproj
            iosApp/iosApp/Info.plist
            ${info.moduleName}/src/wasmJsMain/resources/index.html
            ${info.moduleName}/src/wasmJsMain/kotlin/main.kt
            ${info.moduleName}/src/wasmJsMain/kotlin/org/company/app/theme/Theme.wasmJs.kt
            ${info.moduleName}/src/wasmJsMain/resources/favicon.ico
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat
                import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose.compiler)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                    alias(libs.plugins.apollo)
                    alias(libs.plugins.kotlinx.serialization)
                    alias(libs.plugins.sqlDelight)
                    alias(libs.plugins.buildConfig)
                    alias(libs.plugins.room)
                    alias(libs.plugins.ksp)
                }

                kotlin {
                    jvmToolchain(11)
                    androidTarget {
                        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
                        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
                    }

                    jvm()

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
                            baseName = "ComposeApp"
                            isStatic = true
                        }
                    }

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                            implementation(libs.apollo.runtime)
                            implementation(libs.voyager.navigator)
                            implementation(libs.composeImageLoader)
                            implementation(libs.androidx.navigation.composee)
                            implementation(libs.androidx.lifecycle.runtime.compose)
                            implementation(libs.androidx.lifecycle.viewmodel)
                            implementation(libs.coil)
                            implementation(libs.coil.network.ktor)
                            implementation(libs.kermit)
                            implementation(libs.kotlinx.datetime)
                            implementation(libs.multiplatformSettings)
                            implementation(libs.koin.core)
                            implementation(libs.koin.compose)
                            implementation(libs.kstore)
                            implementation(libs.composeIcons.featherIcons)
                            implementation(libs.ktor.client.core)
                            implementation(libs.kotlinx.coroutines.core)
                            implementation(libs.kotlinx.serialization.json)
                            implementation(libs.room.runtime)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                        androidMain.dependencies {
                            implementation(compose.uiTooling)
                            implementation(libs.androidx.activityCompose)
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.kotlinx.coroutines.android)
                            implementation(libs.sqlDelight.driver.android)
                        }

                        jvmMain.dependencies {
                            implementation(compose.desktop.currentOs)
                            implementation(libs.ktor.client.okhttp)
                            implementation(libs.sqlDelight.driver.sqlite)
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

                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                }
                
                //https://developer.android.com/develop/ui/compose/testing#setup
                dependencies {
                    androidTestImplementation(libs.androidx.uitest.junit4)
                    debugImplementation(libs.androidx.uitest.testManifest)
                    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
                    androidTestImplementation("androidx.test:monitor") {
                        version { strictly("1.6.1") }
                    }
                }

                compose.desktop {
                    application {
                        mainClass = "MainKt"

                        nativeDistributions {
                            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                            packageName = "Multiplatform App"
                            packageVersion = "1.0.0"

                            linux {
                                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
                            }
                            windows {
                                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
                            }
                            macOS {
                                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                                bundleID = "org.company.app.desktopApp"
                            }
                        }
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
                            packageName.set("org.company.app.db")
                        }
                    }
                }
                
                room {
                    schemaDirectory("${projectDir}/schemas")
                }
                
                apollo {
                    service("api") {
                        // GraphQL configuration here.
                        // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/
                        packageName.set("org.company.app.graphql")
                    }
                }
                
                dependencies {
                    with(libs.room.compiler) {
                        add("kspAndroid", this)
                        add("kspJvm", this)
                        add("kspIosX64", this)
                        add("kspIosArm64", this)
                        add("kspIosSimulatorArm64", this)
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            """
                [versions]

                kotlin = "${KotlinPlugin.version}"
                compose = "${ComposePlugin.version}"
                agp = "${AndroidApplicationPlugin.version}"
                androidx-activityCompose = "${AndroidxActivityCompose.version}"
                androidx-uiTest = "${AndroidxJUnit4.version}"
                apollo = "${ApolloRuntime.version}"
                voyager = "${Voyager.version}"
                composeImageLoader = "${ImageLoader.version}"
                androidx-navigation = "${AndroidxNavigation.version}"
                androidx-lifecycle = "${AndroidxLifecycleViewmodel.version}"
                coil = "${Coil.version}"
                kermit = "${Kermit.version}"
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
                room = "${RoomPlugin.version}"
                ksp = "${DevToolKSP.version}"

                [libraries]

                androidx-activityCompose = { module = "androidx.activity:activity-compose", version.ref = "androidx-activityCompose" }
                androidx-uitest-testManifest = { module = "androidx.compose.ui:ui-test-manifest", version.ref = "androidx-uiTest" }
                androidx-uitest-junit4 = { module = "androidx.compose.ui:ui-test-junit4", version.ref = "androidx-uiTest" }
                apollo-runtime = { module = "com.apollographql.apollo:apollo-runtime", version.ref = "apollo" }
                voyager-navigator = { module = "cafe.adriel.voyager:voyager-navigator", version.ref = "voyager" }
                composeImageLoader = { module = "io.github.qdsfdhvh:image-loader", version.ref = "composeImageLoader" }
                androidx-navigation-composee = { module = "org.jetbrains.androidx.navigation:navigation-compose", version.ref = "androidx-navigation" }
                androidx-lifecycle-runtime-compose = { module = "org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose", version.ref = "androidx-lifecycle" }
                androidx-lifecycle-viewmodel = { module = "org.jetbrains.androidx.lifecycle:lifecycle-viewmodel", version.ref = "androidx-lifecycle" }
                coil = { module = "io.coil-kt.coil3:coil-compose-core", version.ref = "coil" }
                coil-network-ktor = { module = "io.coil-kt.coil3:coil-network-ktor3", version.ref = "coil" }
                kermit = { module = "co.touchlab:kermit", version.ref = "kermit" }
                kotlinx-datetime = { module = "org.jetbrains.kotlinx:kotlinx-datetime", version.ref = "kotlinx-datetime" }
                multiplatformSettings = { module = "com.russhwolf:multiplatform-settings", version.ref = "multiplatformSettings" }
                koin-core = { module = "io.insert-koin:koin-core", version.ref = "koin" }
                koin-compose = { module = "io.insert-koin:koin-compose", version.ref = "koin" }
                kstore = { module = "io.github.xxfast:kstore", version.ref = "kstore" }
                composeIcons-featherIcons = { module = "br.com.devsrsouza.compose.icons:feather", version.ref = "composeIcons" }
                ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
                ktor-client-darwin = { module = "io.ktor:ktor-client-darwin", version.ref = "ktor" }
                ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
                ktor-client-js = { module = "io.ktor:ktor-client-js", version.ref = "ktor" }
                kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "kotlinx-coroutines" }
                kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinx-coroutines" }
                kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinx-serialization" }
                sqlDelight-driver-sqlite = { module = "app.cash.sqldelight:sqlite-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-android = { module = "app.cash.sqldelight:android-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-native = { module = "app.cash.sqldelight:native-driver", version.ref = "sqlDelight" }
                sqlDelight-driver-js = { module = "app.cash.sqldelight:web-worker-driver", version.ref = "sqlDelight" }
                room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
                room-compiler = { module = "androidx.room:room-compiler", version.ref = "room" }

                [plugins]

                multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
                compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
                compose = { id = "org.jetbrains.compose", version.ref = "compose" }
                android-application = { id = "com.android.application", version.ref = "agp" }
                apollo = { id = "com.apollographql.apollo", version.ref = "apollo" }
                kotlinx-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
                sqlDelight = { id = "app.cash.sqldelight", version.ref = "sqlDelight" }
                buildConfig = { id = "com.github.gmazzo.buildconfig", version.ref = "buildConfig" }
                room = { id = "androidx.room", version.ref = "room" }
                ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

            """.trimIndent(),
            files.first { it is GradleLibsVersion }.content
        )
    }

    @Test
    fun buildAndroidFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.android.app",
            platforms = setOf(ProjectPlatform.Android)
        )
        val files = info.generateComposeAppFiles()

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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/android/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/android/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/android/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/android/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/androidMain/AndroidManifest.xml
            ${info.moduleName}/src/androidMain/res/mipmap-anydpi-v26/ic_launcher.xml
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-hdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-mdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_background.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_foreground.png
            ${info.moduleName}/src/androidMain/res/mipmap-xxxhdpi/ic_launcher_monochrome.png
            ${info.moduleName}/src/androidMain/kotlin/org/android/app/App.android.kt
            ${info.moduleName}/src/androidMain/kotlin/org/android/app/theme/Theme.android.kt
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
                
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose.compiler)
                    alias(libs.plugins.compose)
                    alias(libs.plugins.android.application)
                }

                kotlin {
                    jvmToolchain(11)
                    androidTarget {
                        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
                        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
                    }

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                        androidMain.dependencies {
                            implementation(compose.uiTooling)
                            implementation(libs.androidx.activityCompose)
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

                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                }
                
                //https://developer.android.com/develop/ui/compose/testing#setup
                dependencies {
                    androidTestImplementation(libs.androidx.uitest.junit4)
                    debugImplementation(libs.androidx.uitest.testManifest)
                    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
                    androidTestImplementation("androidx.test:monitor") {
                        version { strictly("1.6.1") }
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildIosFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.ios.app",
            platforms = setOf(ProjectPlatform.Ios),
            dependencies = setOf(KotlinPlugin, ComposePlugin)
        )
        val files = info.generateComposeAppFiles()

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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/ios/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/ios/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/ios/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/ios/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/iosMain/kotlin/main.kt
            ${info.moduleName}/src/iosMain/kotlin/org/ios/app/theme/Theme.ios.kt
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-20~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-29~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@2x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40@3x.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-40~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-60@2x~car.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-60@3x~car.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon-83.5@2x~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon~ios-marketing.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/AppIcon~ipad.png
            iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json
            iosApp/iosApp/Assets.xcassets/AccentColor.colorset/Contents.json
            iosApp/iosApp/Assets.xcassets/Contents.json
            iosApp/iosApp/Preview Content/Preview Assets.xcassets/Contents.json
            iosApp/iosApp/iosApp.swift
            iosApp/iosApp.xcodeproj/project.xcworkspace/contents.xcworkspacedata
            iosApp/iosApp.xcodeproj/project.pbxproj
            iosApp/iosApp/Info.plist
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                
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
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildDesktopFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.desktop.app",
            platforms = setOf(ProjectPlatform.Jvm),
            dependencies = setOf(KotlinPlugin, ComposePlugin)
        )
        val files = info.generateComposeAppFiles()

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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/desktop/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/desktop/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/desktop/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/desktop/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/jvmMain/kotlin/main.kt
            ${info.moduleName}/src/jvmMain/kotlin/org/desktop/app/theme/Theme.jvm.kt
            ${info.moduleName}/desktopAppIcons/LinuxIcon.png
            ${info.moduleName}/desktopAppIcons/WindowsIcon.ico
            ${info.moduleName}/desktopAppIcons/MacosIcon.icns
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                }

                kotlin {
                    jvm()

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                        jvmMain.dependencies {
                            implementation(compose.desktop.currentOs)
                        }

                    }
                }

                compose.desktop {
                    application {
                        mainClass = "MainKt"

                        nativeDistributions {
                            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                            packageName = "Multiplatform App"
                            packageVersion = "1.0.0"

                            linux {
                                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
                            }
                            windows {
                                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
                            }
                            macOS {
                                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                                bundleID = "org.desktop.app.desktopApp"
                            }
                        }
                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildBrowserJsFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.js.app",
            platforms = setOf(ProjectPlatform.Js),
            dependencies = setOf(KotlinPlugin, ComposePlugin)
        )
        val files = info.generateComposeAppFiles()

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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/js/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/js/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/js/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/js/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/jsMain/resources/index.html
            ${info.moduleName}/src/jsMain/kotlin/main.kt
            ${info.moduleName}/src/jsMain/kotlin/org/js/app/theme/Theme.js.kt
            ${info.moduleName}/src/jsMain/resources/favicon.ico
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                
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
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                        jsMain.dependencies {
                            implementation(compose.html.core)
                        }

                    }
                }

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildBrowserWasmFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.wasm.app",
            platforms = setOf(ProjectPlatform.Wasm),
            dependencies = setOf(KotlinPlugin, ComposePlugin)
        )
        val files = info.generateComposeAppFiles()

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
            ${info.moduleName}/build.gradle.kts
            ${info.moduleName}/src/commonMain/kotlin/org/wasm/app/theme/Color.kt
            ${info.moduleName}/src/commonMain/kotlin/org/wasm/app/theme/Theme.kt
            ${info.moduleName}/src/commonMain/kotlin/org/wasm/app/App.kt
            ${info.moduleName}/src/commonTest/kotlin/org/wasm/app/ComposeTest.kt
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml
            ${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml
            ${info.moduleName}/src/commonMain/composeResources/values/strings.xml
            ${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf
            ${info.moduleName}/src/wasmJsMain/resources/index.html
            ${info.moduleName}/src/wasmJsMain/kotlin/main.kt
            ${info.moduleName}/src/wasmJsMain/kotlin/org/wasm/app/theme/Theme.wasmJs.kt
            ${info.moduleName}/src/wasmJsMain/resources/favicon.ico
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.ExperimentalComposeLibrary
                
                plugins {
                    alias(libs.plugins.multiplatform)
                    alias(libs.plugins.compose)
                }

                kotlin {
                    wasmJs {
                        browser()
                        binaries.executable()
                    }

                    sourceSets {
                        commonMain.dependencies {
                            implementation(compose.runtime)
                            implementation(compose.foundation)
                            implementation(compose.material3)
                            implementation(compose.components.resources)
                            implementation(compose.components.uiToolingPreview)
                        }

                        commonTest.dependencies {
                            implementation(kotlin("test"))
                            @OptIn(ExperimentalComposeLibrary::class)
                            implementation(compose.uiTest)
                        }

                    }
                }
                
            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }
}
