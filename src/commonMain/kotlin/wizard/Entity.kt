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
    Wasm("Wasm"),
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
    val gradleVersion: String = "8.7",
    val androidMinSdk: Int = 24,
    val androidTargetSdk: Int = 34,
    val composeCompilerVersion: String = "1.5.11",
    val dependencies: Set<Dependency>
)

fun ProjectInfo.getResourcesPackage(): String = "$safeName.$moduleName.generated.resources"
    .lowercase().replace('-', '_')

fun DefaultComposeAppInfo() = ProjectInfo(
    packageId = "org.company.app",
    //Shouldn't be "ComposeApp" because it breaks ios build. The reason is kotlin framework name is "ComposeApp"
    name = "Multiplatform App",
    moduleName = "composeApp",
    platforms = setOf(Android, Ios, Jvm, Js),
    dependencies = setOf(
        KotlinPlugin,
        ComposeCompilerPlugin,
        ComposePlugin,
        AndroidApplicationPlugin,
        AndroidxActivityCompose,
        AndroidxTestManifest,
        AndroidxJUnit4,
    )
)

fun DefaultKmpLibraryInfo() = ProjectInfo(
    packageId = "my.company.name",
    name = "KMP library",
    moduleName = "shared",
    platforms = setOf(Android, Ios, Jvm, Js),
    dependencies = setOf(
        KotlinPlugin,
        AndroidLibraryPlugin
    )
)

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

fun Dependency.isPlugin() = id == "gradle-plugin"
fun Dependency.isCommon() = platforms.isEmpty()

val Dependency.catalogAccessor get() = catalogName.replace("-", ".")
val Dependency.libraryNotation get() = "implementation(libs.$catalogAccessor)"
val Dependency.pluginNotation get() = "alias(libs.plugins.$catalogAccessor)"

interface ProjectFile {
    val path: String
    val content: String
}
