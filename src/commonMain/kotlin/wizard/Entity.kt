package wizard

import wizard.ProjectPlatform.*
import wizard.dependencies.*

enum class ProjectPlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Jvm("JVM"),
    Js("JS"),
    Macos("macOS"),
    Linux("Linux"),
    Mingw("Windows"),
    Wasm("Wasm");

    companion object {
        val composePlatforms = listOf(Android, Jvm, Js, Wasm, Ios)
        val binaryPlatforms = listOf(Macos, Linux, Mingw)
    }
}

enum class WizardType {
    ComposeApp,
    KmpLibrary
}

data class ProjectInfo(
    val packageId: String,
    //Shouldn't be "ComposeApp" because it breaks ios build. The reason is kotlin framework name is "ComposeApp"
    val name: String,
    val moduleName: String,
    val platforms: Set<ProjectPlatform>,
    val gradleVersion: String = "8.14.1",
    val androidMinSdk: Int = 21,
    val androidTargetSdk: Int = 35,
    val dependencies: Set<Dependency>,
    val type: WizardType
)

fun ProjectInfo.getResourcesPackage(): String = "$safeName.$moduleName.generated.resources"
    .lowercase().replace('-', '_')

fun DefaultComposeAppInfo() = ProjectInfo(
    packageId = "org.company.app",
    //Shouldn't be "ComposeApp" because it breaks ios build. The reason is kotlin framework name is "ComposeApp"
    name = "Multiplatform App",
    moduleName = "composeApp",
    platforms = setOf(Android, Ios, Jvm, Wasm),
    dependencies = setOf(
        KotlinPlugin,
        ComposeCompilerPlugin,
        ComposePlugin,
        AndroidApplicationPlugin,
        AndroidxActivityCompose,
        AndroidxTestManifest,
        AndroidxJUnit4,
        ComposeHotReloadPlugin,
    ),
    type = WizardType.ComposeApp
)

fun DefaultKmpLibraryInfo() = ProjectInfo(
    packageId = "my.company.name",
    name = "KMP library",
    moduleName = "shared",
    platforms = setOf(Android, Ios, Jvm, Js),
    dependencies = setOf(
        KotlinPlugin,
        AndroidLibraryPlugin,
        MavenPublishPlugin,
    ),
    type = WizardType.KmpLibrary
)

val ProjectInfo.needComposeSample: Boolean
    get() = type == WizardType.KmpLibrary && platforms.any { it in ProjectPlatform.composePlatforms }

val ProjectInfo.needTerminalSample: Boolean
    get() = type == WizardType.KmpLibrary && platforms.any { it in ProjectPlatform.binaryPlatforms }

val ProjectInfo.enableJvmHotReload: Boolean
    get() = dependencies.contains(ComposeHotReloadPlugin) && platforms.contains(Jvm)

fun ProjectInfo.hasPlatform(platform: ProjectPlatform) = platforms.contains(platform)
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")

fun ProjectInfo.generate(type: WizardType) = when (type) {
    WizardType.ComposeApp -> generateComposeAppFiles()
    WizardType.KmpLibrary -> generateKmpLibraryFiles()
}

data class Dependency(
    val title: String,
    val description: String,
    val url: String,
    val group: String,
    val id: String,
    val version: String,
    val catalogVersionName: String,
    val catalogName: String,
    val platforms: Set<ProjectPlatform>,
    val isTestDependency: Boolean = false,
    val isKspDependency: Boolean = false
)

fun Dependency.hasPlatform(platform: ProjectPlatform) = platforms.contains(platform)
fun Dependency.isPlugin() = id == "gradle-plugin"
fun Dependency.isKSP() = isKspDependency
fun Dependency.isCommon() = platforms.isEmpty()

val Dependency.catalogAccessor get() = catalogName.replace("-", ".")
val Dependency.libraryNotation get() = "implementation(libs.$catalogAccessor)"
val Dependency.pluginNotation get() = "alias(libs.plugins.$catalogAccessor)"

interface ProjectFile {
    val path: String
    val content: String
}

interface BinaryFile : ProjectFile {
    override val content get() = error("It is a binary file")
    val resourcePath: String
}
