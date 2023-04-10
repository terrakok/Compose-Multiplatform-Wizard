package wizard

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import wizard.files.GradleBat
import wizard.files.GradleWrapperJar
import wizard.files.Gradlew
import java.io.File
import java.lang.ProcessBuilder.Redirect
import kotlin.io.path.createTempDirectory
import kotlin.test.assertEquals

internal val allDependencies = setOf(
    AndroidxAppcompat,
    AndroidxActivityCompose,
    ApolloPlugin,
    ApolloRuntime,
    ComposeUiTooling,
    LibresPlugin,
    LibresCompose,
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
    KotlinxSerializationPlugin,
    KotlinxSerializationJson,
    SQLDelightPlugin,
    SQLDelightDriverJvm,
    SQLDelightDriverAndroid,
    SQLDelightDriverNative,
    SQLDelightDriverJs,
    BuildConfigPlugin,
)

class GeneratedProjectTest {

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
            ProjectInfo(
                platforms = setOf(ComposePlatform.Desktop, ComposePlatform.Browser),
                dependencies = allDependencies
            )
        )
    }

    @Test
    fun testDesktopProject() {
        checkProject(
            ProjectInfo(
                packageId = "com.test.unit.app",
                name = "DesktopApp",
                platforms = setOf(ComposePlatform.Desktop),
                dependencies = setOf(
                    LibresPlugin,
                    LibresCompose,
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
            ProjectInfo(
                packageId = "io.js.app.test",
                name = "test js compose app",
                platforms = setOf(ComposePlatform.Browser),
                dependencies = setOf(
                    Napier,
                    KotlinxDateTime,
                    MultiplatformSettings,
                    KotlinxCoroutinesCore,
                    BuildConfigPlugin,
                )
            )
        )
    }

    @Test
    fun checkDependencyUpdates() {
        val projectInfo = ProjectInfo(
            platforms = setOf(ComposePlatform.Desktop),
            dependencies = allDependencies
        )
        val dir = projectInfo.writeToDir(workingDir)
        dir.resolve("composeApp/build.gradle.kts").apply {
            writeText(
                readText().replace(
                    "plugins {",
                    """
                        plugins {
                            id("com.github.ben-manes.versions").version("0.46.0")
                    """.trimIndent()
                )
            )
        }

        println("Project dir: ${dir.absolutePath}")
        println("============start of the build============")
        val proc = ProcessBuilder("${dir.path}/gradlew", "dependencyUpdates").apply {
            directory(dir)
            redirectOutput(Redirect.INHERIT)
            redirectError(Redirect.INHERIT)
        }.start()

        proc.waitFor()
        println("============end of the build============")
        assertEquals(0, proc.exitValue(), "'./gradlew dependencyUpdates' exit code")
    }

    private fun checkProject(projectInfo: ProjectInfo) {
        val dir = projectInfo.writeToDir(workingDir)

        println("Project dir: ${dir.absolutePath}")
        println("============start of the build============")
        val proc = ProcessBuilder("${dir.path}/gradlew", "build", "--info").apply {
            directory(dir)
            redirectOutput(Redirect.INHERIT)
            redirectError(Redirect.INHERIT)
        }.start()

        proc.waitFor()
        println("============end of the build============")
        assertEquals(0, proc.exitValue(), "'./gradlew build --info' exit code")

    }

    private fun ProjectInfo.writeToDir(workingDir: File): File {
        val dir = workingDir.resolve(safeName)
        dir.deleteRecursively()
        dir.mkdirs()

        buildFiles().forEach { projectFile ->
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