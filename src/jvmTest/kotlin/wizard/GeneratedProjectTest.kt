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
    fun testGeneratedProject() {
        checkProject(
            ProjectInfo(
                platforms = setOf(ComposePlatform.Desktop),
                dependencies = setOf(
                    AndroidxAppcompat,
                    AndroidxActivityCompose,
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
                    SQLDelightDriverJs
                )
            )
        )
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