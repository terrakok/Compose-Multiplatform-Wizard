package wizard

import wizard.dependencies.*
import wizard.files.composeApp.ModuleBuildGradleKts
import wizard.files.composeApp.Readme
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppGeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = DefaultComposeAppInfo().copy(
            dependencies =  buildSet {
                add(KotlinMultiplatformPlugin)
                add(ComposeCompilerPlugin)
                add(ComposeMultiplatformPlugin)
                addAll(androidDependencies)
                addAll(extraDependencies)
                addAll(roomDependencies)
            }
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildAllFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            readResourceFileText("buildAllFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )

        assertEquals(
            readResourceFileText("buildAllFiles/README.MD"),
            files.first { it is Readme }.content
        )
    }

    @Test
    fun buildAndroidFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.android.app",
            platforms = setOf(ProjectPlatform.Android),
            dependencies = setOf(
                KotlinMultiplatformPlugin,
                ComposeCompilerPlugin,
                ComposeMultiplatformPlugin,
                AndroidApplicationPlugin,
                AndroidxActivityCompose,
                AndroidxTestManifest,
                AndroidxJUnit4,
            ),
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildAndroidFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            readResourceFileText("buildAndroidFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildIosFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.ios.app",
            platforms = setOf(ProjectPlatform.Ios),
            dependencies = setOf(KotlinMultiplatformPlugin, ComposeMultiplatformPlugin)
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildIosFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            readResourceFileText("buildIosFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildDesktopFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.desktop.app",
            platforms = setOf(ProjectPlatform.Jvm),
            dependencies = setOf(KotlinMultiplatformPlugin, ComposeMultiplatformPlugin, ComposeHotReloadPlugin)
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildDesktopFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )
        assertEquals(
            readResourceFileText("buildDesktopFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildBrowserJsFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.js.app",
            platforms = setOf(ProjectPlatform.Js),
            dependencies = setOf(KotlinMultiplatformPlugin, ComposeMultiplatformPlugin)
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildBrowserJsFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            readResourceFileText("buildBrowserJsFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildBrowserWasmFiles() {
        val info = DefaultComposeAppInfo().copy(
            packageId = "org.wasm.app",
            platforms = setOf(ProjectPlatform.Wasm),
            dependencies = setOf(KotlinMultiplatformPlugin, ComposeMultiplatformPlugin)
        )
        val files = info.generateComposeAppFiles()

        assertEquals(
            readResourceFileText("buildBrowserWasmFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )

        assertEquals(
            readResourceFileText("buildBrowserWasmFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    private fun readResourceFileText(path: String): String = javaClass.classLoader
        .getResource("ComposeAppGenerator/$path").readText()
}
