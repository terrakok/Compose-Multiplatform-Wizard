package wizard

import org.junit.*
import wizard.dependencies.*
import wizard.files.*
import java.io.*
import javax.json.Json
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals

internal val androidDependencies = setOf(
    AndroidApplicationPlugin,
    AndroidxActivityCompose,
    AndroidxTestManifest,
    AndroidxJUnit4,
)

internal val extraDependencies = setOf(
    ApolloPlugin,
    ApolloRuntime,
    Voyager,
    ImageLoader,
    AndroidxNavigation,
    AndroidxLifecycleRuntime,
    AndroidxLifecycleViewmodel,
    Coil,
    CoilNetwork,
    Sketch,
    SketchHttp,
    Kermit,
    Napier,
    KotlinxDateTime,
    MultiplatformSettings,
    Koin,
    KoinCompose,
    KStore,
    KtorCore,
    KtorClientDarwin,
    KtorClientOkhttp,
    KtorClientJs,
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
    MaterialKolor
)

internal val roomDependencies = setOf(
    RoomPlugin,
    RoomPluginRuntime,
    RoomPluginCompiler,
    DevToolKSP
)

class GeneratedComposeAppProjectTest {

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
    fun testDesktopAndBrowserProject() {
        checkProject(
            DefaultComposeAppInfo().copy(
                platforms = setOf(ProjectPlatform.Jvm, ProjectPlatform.Wasm, ProjectPlatform.Js),
                dependencies = setOf(
                    KotlinMultiplatformPlugin,
                    KotlinJvmPlugin,
                    ComposeCompilerPlugin,
                    ComposeMultiplatformPlugin,
                    ComposeHotReloadPlugin,
                    Voyager,
                    Napier,
                    KotlinxDateTime,
                    MultiplatformSettings,
                    Koin,
                    KtorCore,
                    KtorClientOkhttp,
                    KotlinxCoroutinesCore,
                    KotlinxSerializationPlugin,
                    KotlinxSerializationJson,
                    BuildConfigPlugin,
                )
            ),
            taskName = "assemble"
        )
    }

    @Test
    fun testDesktopProject() {
        checkProject(
            DefaultComposeAppInfo().copy(
                packageId = "com.test.unit.app",
                name = "DesktopApp",
                platforms = setOf(ProjectPlatform.Jvm),
                dependencies = buildSet {
                    add(KotlinMultiplatformPlugin)
                    add(KotlinJvmPlugin)
                    add(ComposeCompilerPlugin)
                    add(ComposeMultiplatformPlugin)
                    add(ComposeHotReloadPlugin)
                    addAll(extraDependencies)
                    addAll(roomDependencies)
                }
            )
        )
    }

    @Test
    fun testWebProject() {
        checkProject(
            DefaultComposeAppInfo().copy(
                packageId = "io.web.app.test",
                name = "test web compose app",
                platforms = setOf(ProjectPlatform.Wasm, ProjectPlatform.Js),
                dependencies = setOf(
                    KotlinMultiplatformPlugin,
                    ComposeCompilerPlugin,
                    ComposeMultiplatformPlugin,
                    Napier,
                    KotlinxDateTime,
                    MultiplatformSettings,
                    KotlinxCoroutinesCore,
                    BuildConfigPlugin,
                )
            ),
            taskName = "assemble"
        )
    }

    @Test
    @Ignore("Android UI tests are not supported yet")
    fun testAndroidProject() {
        val projectInfo = DefaultComposeAppInfo()
        val dir = projectInfo.writeToDir(workingDir)
        dir.resolve("composeApp/build.gradle.kts").apply {
            writeText(
                readText() + """
                    |
                    |android.testOptions.managedDevices.devices.maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel5").apply {
                    |    device = "Pixel 5"
                    |    apiLevel = 34
                    |    systemImageSource = "aosp"
                    |}
                """.trimMargin()
            )
        }
        checkCommand(
            dir = dir,
            command = mutableListOf(
                "${dir.path}/gradlew",
                ":${projectInfo.moduleName}:assembleDebug",
                ":${projectInfo.moduleName}:allTests"
            ).also {
                if (!(System.getProperty("os.name").contains(other = "mac", ignoreCase = true) &&
                        "true".equals(other = System.getenv("CI"), ignoreCase = true))
                ) {
                    // Run Android native test on Ubuntu build agent only
                    it.add(":${projectInfo.moduleName}:pixel5Check")
                }
                it.add("--stacktrace")
            }
        )
    }

    @Test
    fun testIosProject() {
        val osName = System.getProperty("os.name")
        if (!osName.contains(other = "mac", ignoreCase = true)) {
            println("iOS testing is skipped because this machine ($osName) isn't Mac")
            return
        }
        val projectInfo = DefaultComposeAppInfo().copy(
            platforms = setOf(ProjectPlatform.Ios),
            dependencies = buildSet {
                add(KotlinMultiplatformPlugin)
                add(ComposeCompilerPlugin)
                add(ComposeMultiplatformPlugin)
                addAll(extraDependencies)
                addAll(roomDependencies)
            }
        )
        val dir = projectInfo.writeToDir(workingDir)
        val devicesJson = checkCommand(
            dir = dir,
            command = listOf(
                "xcrun",
                "simctl",
                "list",
                "devices",
                "available",
                "-j"
            )
        )
        val devicesList = Json.createReader(StringReader(devicesJson)).use { it.readObject() }
            .getJsonObject("devices")
            .let { devicesMap ->
                devicesMap.keys
                    .filter { it.startsWith("com.apple.CoreSimulator.SimRuntime.iOS") }
                    .map { devicesMap.getJsonArray(it) }
            }
            .map { jsonArray -> jsonArray.map { it.asJsonObject() } }
            .flatten()
            .filter { it.getBoolean("isAvailable") }
            .filter {
                if (System.getenv("CI").toBoolean()) {
                    listOf("iphone 15", "iphone 14").any { device ->
                        it.getString("name").contains(device, true)
                    }
                } else {
                    true
                }
            }
        println("Devices:${devicesList.joinToString { "\n" + it["udid"] + ": " + it["name"] }}")
        val deviceId = devicesList.firstOrNull()?.getString("udid")
        checkCommand(
            dir = dir,
            command = listOf(
                "xcodebuild",
                "-project",
                "${dir.path}/iosApp/iosApp.xcodeproj",
                "-scheme",
                "iosApp",
                "-configuration",
                "Debug",
                "OBJROOT=${dir.path}/build/ios",
                "SYMROOT=${dir.path}/build/ios",
                "-destination",
                "id=$deviceId",
                "-allowProvisioningDeviceRegistration",
                "-allowProvisioningUpdates"
            )
        )
    }

    @Test
    fun checkDependencyUpdates() {
        val projectInfo = DefaultComposeAppInfo().copy(
            platforms = setOf(ProjectPlatform.Jvm),
            dependencies = buildSet {
                add(KotlinMultiplatformPlugin)
                add(KotlinJvmPlugin)
                add(ComposeCompilerPlugin)
                add(ComposeMultiplatformPlugin)
                add(ComposeHotReloadPlugin)
                addAll(extraDependencies)
                addAll(roomDependencies)
                add(BuildKonfigPlugin)
                add(SQLDelightPlugin)
                add(Kodein)
                add(PreCompose)
                add(Decompose)
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
                ) + """
                    |
                    |tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
                    |    rejectVersionIf { 
                    |       isNonStable(candidate.version) && !isNonStable(currentVersion) 
                    |    }
                    |}
                    |
                    |fun isNonStable(version: String): Boolean {
                    |    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
                    |    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
                    |    val isStable = stableKeyword || regex.matches(version)
                    |    return isStable.not()
                    |}
                """.trimMargin()
            )
        }
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", "dependencyUpdates", "--no-parallel")
        )
    }

    private fun checkProject(projectInfo: ProjectInfo, taskName: String = "build") {
        val dir = projectInfo.writeToDir(workingDir)
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", taskName, "--info")
        )
    }

    private fun checkCommand(dir: File, command: List<String>): String {
        println("Project dir: ${dir.absolutePath}")
        println("command: ${command.joinToString(" ")}")
        println("============start of the command============")
        val proc = ProcessBuilder(command).apply { directory(dir) }.start()
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(proc.inputStream)).forEachLine {
            println(it)
            stringBuilder.appendLine(it)
        }
        proc.errorStream.printStream()
        proc.waitFor()
        println("============end of the command============")
        assertEquals(0, proc.exitValue(), "command exit code")
        return stringBuilder.toString()
    }

    private fun InputStream.printStream() {
        BufferedReader(InputStreamReader(this)).forEachLine { println(it) }
    }

    private fun ProjectInfo.writeToDir(workingDir: File): File {
        val dir = workingDir.resolve(safeName)
        dir.deleteRecursively()
        dir.mkdirs()

        generateComposeAppFiles().forEach { projectFile ->
            val f = dir.resolve(projectFile.path)
            f.parentFile.mkdirs()
            f.createNewFile()

            if (projectFile is BinaryFile) {
                f.outputStream().use { out ->
                    javaClass.getResourceAsStream("/binaries/${projectFile.resourcePath}")
                        .use { it!!.copyTo(out) }
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
