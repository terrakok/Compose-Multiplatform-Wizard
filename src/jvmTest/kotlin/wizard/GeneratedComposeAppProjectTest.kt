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
    Napier,
    KotlinxDateTime,
    MultiplatformSettings,
    Koin,
    KStore,
    ComposeIconsFeather,
    KtorCore,
    KtorClientDarwin,
    KtorClientOkhttp,
    KtorClientJs,
    KotlinxCoroutinesCore,
    KotlinxCoroutinesAndroid,
    MokoMvvm,
    KotlinxSerializationPlugin,
    KotlinxSerializationJson,
    SQLDelightPlugin,
    SQLDelightDriverJvm,
    SQLDelightDriverAndroid,
    SQLDelightDriverNative,
    SQLDelightDriverJs,
    BuildConfigPlugin,
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
                platforms = setOf(ProjectPlatform.Jvm, ProjectPlatform.Js),
                dependencies = buildSet {
                    add(KotlinPlugin)
                    add(ComposeCompilerPlugin)
                    add(ComposePlugin)
                    addAll(extraDependencies)
                }
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
                dependencies = setOf(
                    KotlinPlugin,
                    ComposeCompilerPlugin,
                    ComposePlugin,
                    Voyager,
                    ImageLoader,
                    Napier,
                    KotlinxDateTime,
                    MultiplatformSettings,
                    Koin,
                    KStore,
                    KtorCore,
                    KtorClientOkhttp,
                    KotlinxCoroutinesCore,
                    KotlinxSerializationPlugin,
                    KotlinxSerializationJson,
                    SQLDelightPlugin,
                    SQLDelightDriverJvm,
                    BuildConfigPlugin,
                )
            )
        )
    }

    @Test
    fun testBrowserProject() {
        checkProject(
            DefaultComposeAppInfo().copy(
                packageId = "io.js.app.test",
                name = "test js compose app",
                platforms = setOf(ProjectPlatform.Js),
                dependencies = setOf(
                    KotlinPlugin,
                    ComposeCompilerPlugin,
                    ComposePlugin,
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
    fun testAndroidProject() {
        val projectInfo = DefaultComposeAppInfo()
        val dir = projectInfo.writeToDir(workingDir)
        checkCommand(
            dir = dir,
            command = mutableListOf(
                "${dir.path}/gradlew",
                ":${projectInfo.moduleName}:assembleDebug"
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
                add(KotlinPlugin)
                add(ComposeCompilerPlugin)
                add(ComposePlugin)
                addAll(extraDependencies)
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
                add(KotlinPlugin)
                add(ComposeCompilerPlugin)
                add(ComposePlugin)
                addAll(extraDependencies)
            }
        )
        val dir = projectInfo.writeToDir(workingDir)
        dir.resolve("${projectInfo.moduleName}/build.gradle.kts").apply {
            writeText(
                readText().replace(
                    "plugins {",
                    """
                        plugins {
                            id("com.github.ben-manes.versions").version("0.50.0")
                    """.trimIndent()
                )
            )
        }
        checkCommand(
            dir = dir,
            command = listOf("${dir.path}/gradlew", "dependencyUpdates")
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
