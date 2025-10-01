package wizard

import org.junit.*
import wizard.dependencies.*
import wizard.files.*
import java.io.*
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals

internal val kmpLibraryExtraDependencies = setOf(
    KotlinxDateTime,
    MultiplatformSettings,
    Koin,
    Kermit,
    KStore,
    KtorCore,
    KtorClientDarwin,
    KtorClientOkhttp,
    KtorClientJs,
    KtorClientLinux,
    KtorClientMingw,
    KotlinxCoroutinesCore,
    KotlinxCoroutinesAndroid,
    KotlinxSerializationPlugin,
    KotlinxSerializationJson,
    SQLDelightPlugin,
    SQLDelightDriverJvm,
    SQLDelightDriverAndroid,
    SQLDelightDriverNative,
    SQLDelightDriverJs,
    BuildConfigPlugin,
)

class GeneratedKmpLibraryProjectTest {

    companion object {
        private lateinit var workingDir: File

        @BeforeClass
        @JvmStatic
        fun prepare() {
            workingDir = createTempDirectory().toFile()
        }

        @AfterClass
        @JvmStatic
        fun release() {
            workingDir.deleteRecursively()
        }
    }

    @Test
    fun testJvmAndLinuxProject() {
        checkProject(
            DefaultKmpLibraryInfo().copy(
                platforms = setOf(ProjectPlatform.Jvm, ProjectPlatform.Linux),
                dependencies = buildSet {
                    add(KotlinMultiplatformPlugin)
                    add(MavenPublishPlugin)
                }
            )
        )
    }

    @Test
    fun testDesktopProject() {
        checkProject(
            DefaultKmpLibraryInfo().copy(
                packageId = "com.test.unit.app",
                name = "DesktopApp",
                platforms = setOf(ProjectPlatform.Jvm),
                dependencies = buildSet {
                    add(KotlinMultiplatformPlugin)
                    add(MavenPublishPlugin)
                    addAll(kmpLibraryExtraDependencies)
                }
            )
        )
    }

    @Test
    fun testLinuxProject() {
        checkProject(
            DefaultKmpLibraryInfo().copy(
                packageId = "io.linux",
                name = "test linux",
                platforms = setOf(ProjectPlatform.Linux),
                dependencies = setOf(
                    KotlinMultiplatformPlugin,
                    MavenPublishPlugin,
                    KotlinxDateTime,
                )
            )
        )
    }

    @Test
    fun testAndroidProject() {
        checkProject(
            DefaultKmpLibraryInfo().copy(
                packageId = "io.js.app.test",
                name = "test js compose app",
                platforms = setOf(ProjectPlatform.Android),
                dependencies = setOf(
                    KotlinMultiplatformPlugin,
                    MavenPublishPlugin,
                    AndroidLibraryPlugin,
                )
            )
        )
    }

    @Test
    fun checkDependencyUpdates() {
        val projectInfo = DefaultKmpLibraryInfo().copy(
            platforms = setOf(ProjectPlatform.Jvm),
            dependencies = buildSet {
                add(KotlinMultiplatformPlugin)
                add(MavenPublishPlugin)
                addAll(kmpLibraryExtraDependencies)
            }
        )
        val dir = projectInfo.writeToDir(workingDir)
        dir.resolve("${projectInfo.moduleName}/build.gradle.kts").apply {
            writeText(
                readText().replace(
                    "plugins {",
                    """
                        plugins {
                            id("com.github.ben-manes.versions").version("0.53.0")
                    """.trimIndent()
                )
            )
        }
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", "dependencyUpdates")
        )
    }

    @Test
    fun checkPublishToMavenLocal() {
        val projectInfo = DefaultKmpLibraryInfo().copy(
            platforms = setOf(
                ProjectPlatform.Android,
                ProjectPlatform.Ios,
                ProjectPlatform.Jvm,
                ProjectPlatform.Js,
                ProjectPlatform.Macos,
                ProjectPlatform.Linux,
                ProjectPlatform.Mingw
            ),
            dependencies = buildSet {
                add(KotlinMultiplatformPlugin)
                add(MavenPublishPlugin)
                add(AndroidLibraryPlugin)
                add(Koin)
                add(Kermit)
                add(KStore)
            }
        )
        val dir = projectInfo.writeToDir(workingDir)
        dir.resolve("${projectInfo.moduleName}/build.gradle.kts").apply {
            writeText(
                readText() + """
                        version = "1.0.0"
                        publishing {
                            repositories {
                                maven {
                                    name = "custom"
                                    url = projectDir.resolve("repo").toURI()
                                }
                            }
                        }
                    """.trimIndent()
            )
        }
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", "publishAllPublicationsToCustomRepository")
        )

        val konanAbiVersion = "2.2.0"
        val metadata = dir.resolve("${projectInfo.moduleName}/repo/my/company/name/shared/1.0.0/shared-1.0.0-kotlin-tooling-metadata.json").readText()
        assertEquals(
            """
                {
                  "schemaVersion": "1.1.0",
                  "buildSystem": "Gradle",
                  "buildSystemVersion": "${projectInfo.gradleVersion}",
                  "buildPlugin": "org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper",
                  "buildPluginVersion": "${KotlinMultiplatformPlugin.version}",
                  "projectSettings": {
                    "isHmppEnabled": true,
                    "isCompatibilityMetadataVariantEnabled": false,
                    "isKPMEnabled": false
                  },
                  "projectTargets": [
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget",
                      "platformType": "androidJvm",
                      "extras": {
                        "android": {
                          "sourceCompatibility": "17",
                          "targetCompatibility": "17"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "ios_arm64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "ios_simulator_arm64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "ios_x64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget",
                      "platformType": "js",
                      "extras": {
                        "js": {
                          "isBrowserConfigured": true,
                          "isNodejsConfigured": false
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget",
                      "platformType": "jvm",
                      "extras": {
                        "jvm": {
                          "jvmTarget": "17",
                          "withJavaEnabled": false
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "linux_x64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "macos_arm64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "macos_x64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinMetadataTarget",
                      "platformType": "common"
                    },
                    {
                      "target": "org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests",
                      "platformType": "native",
                      "extras": {
                        "native": {
                          "konanTarget": "mingw_x64",
                          "konanVersion": "${KotlinMultiplatformPlugin.version}",
                          "konanAbiVersion": "$konanAbiVersion"
                        }
                      }
                    }
                  ]
                }
            """.trimIndent(),
            metadata
        )
    }

    private fun checkProject(projectInfo: ProjectInfo) {
        val dir = projectInfo.writeToDir(workingDir)
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", "build", "--info")
        )
    }

    private fun checkCommand(dir: File, command: List<String>) {
        println("Project dir: ${dir.absolutePath}")
        println("command: ${command.joinToString(" ")}")
        println("============start of the command============")
        val proc = ProcessBuilder(command).apply { directory(dir) }.start()
        proc.inputStream.printStream()
        proc.errorStream.printStream()
        proc.waitFor()
        println("============end of the command============")
        assertEquals(0, proc.exitValue(), "command exit code")
    }

    private fun InputStream.printStream() {
        BufferedReader(InputStreamReader(this)).forEachLine { println(it) }
    }

    private fun ProjectInfo.writeToDir(workingDir: File): File {
        val dir = workingDir.resolve(safeName)
        dir.deleteRecursively()
        dir.mkdirs()

        generateKmpLibraryFiles().forEach { projectFile ->
            val f = dir.resolve(projectFile.path)
            f.parentFile.mkdirs()
            f.createNewFile()

            if (projectFile is GradleWrapperJar) {
                f.outputStream().use { out ->
                    javaClass.getResourceAsStream("/binaries/gradle-wrapper").use { it!!.copyTo(out) }
                }
            } else {
                if (projectFile is Gradlew) f.setExecutable(true)
                if (projectFile is GradleBat) f.setExecutable(true)

                f.writeText(projectFile.content)
            }
        }
        return dir
    }
}
