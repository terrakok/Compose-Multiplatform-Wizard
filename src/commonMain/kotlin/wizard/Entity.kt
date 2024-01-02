package wizard

import wizard.ProjectPlatform.*
import wizard.dependencies.*

enum class ProjectPlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Jvm("Desktop"),
    Js("Browser"),
}

data class ProjectInfo(
    val packageId: String = "org.company.app",
    //Shouldn't be "ComposeApp" because it breaks ios build. The reason is kotlin framework name is "ComposeApp"
    val name: String = "Multiplatform App",
    val moduleName: String = "composeApp",
    val platforms: Set<ProjectPlatform> = setOf(Android, Ios, Jvm, Js),
    val gradleVersion: String = "8.5",
    val androidMinSdk: Int = 24,
    val androidTargetSdk: Int = 34,
    val composeCompilerVersion: String = "1.5.4",
    val dependencies: Set<Dependency> = setOf(
        KotlinPlugin,
        ComposePlugin,
        AndroidApplicationPlugin,
        AndroidxAppcompat,
        AndroidxActivityCompose,
        ComposeUiTooling
    )
)

fun ProjectInfo.hasPlatform(platform: ProjectPlatform) = platforms.contains(platform)
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")

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
