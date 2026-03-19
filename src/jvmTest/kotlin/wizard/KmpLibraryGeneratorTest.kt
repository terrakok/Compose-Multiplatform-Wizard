package wizard

import wizard.ProjectPlatform.*
import wizard.dependencies.*
import wizard.files.kmpLibrary.Readme
import wizard.files.kmpLibrary.ModuleBuildGradleKts
import wizard.files.kmpLibrary.sample.SampleSharedUIBuildGradleKts
import kotlin.test.Test
import kotlin.test.assertEquals

private val kmpLibraryExtraDependencies = setOf(
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

class GeneratorTest {

    @Test
    fun buildAllFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            platforms = setOf(Android, Ios, Jvm, Js, Macos, Linux, Mingw, Wasm),
            addSampleApp = true,
            dependencies =  buildSet {
                add(KotlinMultiplatformPlugin)
                add(MavenPublishPlugin)
                addAll(kmpLibraryExtraDependencies)
            }
        )
        val files = info.generateKmpLibraryFiles()

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
    fun buildJvmFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "org.desktop.app",
            moduleName = "awesome",
            platforms = setOf(Jvm),
            addSampleApp = true,
            dependencies = setOf(KotlinMultiplatformPlugin, MavenPublishPlugin)
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            readResourceFileText("buildJvmFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )
        assertEquals(
            readResourceFileText("buildJvmFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
    }

    @Test
    fun buildJvmAndAndroidFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "my.company",
            moduleName = "foo",
            platforms = setOf(Jvm, Android),
            addSampleApp = true
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            readResourceFileText("buildJvmAndAndroidFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )
        assertEquals(
            readResourceFileText("buildJvmAndAndroidFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
        assertEquals(
            readResourceFileText("buildJvmAndAndroidFiles/sample.gradle.kts"),
            files.first { it is SampleSharedUIBuildGradleKts }.content
        )
    }

    @Test
    fun buildNativeFiles() {
        val info = DefaultKmpLibraryInfo().copy(
            packageId = "io.my.com",
            moduleName = "lamba",
            platforms = setOf(Ios, Linux),
            addSampleApp = true
        )
        val files = info.generateKmpLibraryFiles()

        assertEquals(
            readResourceFileText("buildNativeFiles/files.txt"),
            files.joinToString("\n") { it.path }
        )
        assertEquals(
            readResourceFileText("buildNativeFiles/build.gradle.kts"),
            files.first { it is ModuleBuildGradleKts }.content
        )
        assertEquals(
            readResourceFileText("buildNativeFiles/sample.gradle.kts"),
            files.first { it is SampleSharedUIBuildGradleKts }.content
        )
    }

    @Test
    fun buildReadmeWithoutSampleApp() {
        val info = DefaultKmpLibraryInfo().copy(
            addSampleApp = false
        )
        val files = info.generateKmpLibraryFiles()

        val readmeContent = files.first { it is Readme }.content
        assertEquals(
            """
                # KMP library
                
                Kotlin Multiplatform Library
                
                ### Publish to MavenLocal
                
                1) Run `./gradlew :shared:publishToMavenLocal`
                2) Open `~/.m2/repository/my/company/name/`
                
                ### Publish to MavenCentral
                
                1) Create an account and a namespace on Sonatype:  
                   https://central.sonatype.org/register/central-portal/#create-an-account
                2) Add developer id, name, email and the project url to  
                   `./shared/build.gradle.kts`
                3) Generate a GPG key:  
                   https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/#generating-a-gpg-key-pair
                   ```
                   gpg --full-gen-key
                   gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXX
                   gpg --export-secret-key XXXXXXXX > XXXXXXXX.gpg
                   ```
                4) Add these lines to `gradle.properties`:
                   ```
                   signing.keyId=XXXXXXXX
                   signing.password=[key password]
                   signing.secretKeyRingFile=../XXXXXXXX.gpg
                   mavenCentralUsername=[generated username]
                   mavenCentralPassword=[generated password]
                   ```
                5) Run `./gradlew :shared:publishAndReleaseToMavenCentral --no-configuration-cache`

            """.trimIndent(),
            readmeContent
        )
    }

    private fun readResourceFileText(path: String): String = javaClass.classLoader
        .getResource("KmpLibraryGenerator/$path").readText()
}
