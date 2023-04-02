package wizard

import wizard.files.app.ModuleBuildGradleKts
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = ProjectInfo()
        val files = info.buildFiles()

        assertEquals(
            """
            .gitignore
            README.MD
            gradle.bat
            gradlew
            gradle/wrapper/gradle-wrapper.properties
            gradle/wrapper/gradle-wrapper.jar
            gradle.properties
            build.gradle.kts
            settings.gradle.kts
            composeApp/build.gradle.kts
            composeApp/src/commonMain/kotlin/org/company/app/Color.kt
            composeApp/src/commonMain/kotlin/org/company/app/Theme.kt
            composeApp/src/commonMain/kotlin/org/company/app/App.kt
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
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                import org.jetbrains.compose.desktop.application.dsl.TargetFormat

                plugins {
                    kotlin("multiplatform")
                    id("org.jetbrains.compose")
                    kotlin("native.cocoapods")
                    id("com.android.application")
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
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val androidMain by getting {
                            dependencies {
                                implementation("androidx.appcompat:appcompat:1.6.1")
                                implementation("androidx.activity:activity-compose:1.7.0")
                                implementation("androidx.compose.ui:ui-tooling:1.4.0")
                            }
                        }

                        val desktopMain by getting {
                            dependencies {
                                implementation(compose.desktop.common)
                                implementation(compose.desktop.currentOs)
                            }
                        }

                        val jsMain by getting {
                            dependencies {
                                implementation(compose.web.core)
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

            """.trimIndent(),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildAndroidFiles() {
        val info = ProjectInfo(
            packageId = "org.android.app",
            platforms = setOf(ComposePlatform.Android)
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
                    kotlin("multiplatform")
                    id("org.jetbrains.compose")
                    id("com.android.application")
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
                            }
                        }

                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }

                        val androidMain by getting {
                            dependencies {
                                implementation("androidx.appcompat:appcompat:1.6.1")
                                implementation("androidx.activity:activity-compose:1.7.0")
                                implementation("androidx.compose.ui:ui-tooling:1.4.0")
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
                    kotlin("multiplatform")
                    id("org.jetbrains.compose")
                    kotlin("native.cocoapods")
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
                    }

                    sourceSets {
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
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
                    kotlin("multiplatform")
                    id("org.jetbrains.compose")
                }

                kotlin {
                    jvm("desktop")

                    sourceSets {
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
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
        """.trimIndent(),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            """
                plugins {
                    kotlin("multiplatform")
                    id("org.jetbrains.compose")
                }

                kotlin {
                    js {
                        browser()
                        binaries.executable()
                    }

                    sourceSets {
                        val commonMain by getting {
                            dependencies {
                                implementation(compose.runtime)
                                implementation(compose.foundation)
                                implementation(compose.material)
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
