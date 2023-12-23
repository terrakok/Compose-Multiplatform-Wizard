package wizard

enum class ComposePlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Desktop("Desktop"),
    Browser("Browser"),
}

val AllPlatforms = setOf(ComposePlatform.Android, ComposePlatform.Ios, ComposePlatform.Desktop, ComposePlatform.Browser)

data class ProjectInfo(
    val packageId: String = "org.company.app",
    //Shouldn't be "ComposeApp" because it breaks ios build. The reason is kotlin framework name is "ComposeApp"
    val name: String = "Multiplatform App",
    val platforms: Set<ComposePlatform> = AllPlatforms,
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

fun ProjectInfo.hasPlatform(platform: ComposePlatform) = platforms.any { it == platform }
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
    val platforms: Set<ComposePlatform>,
)

fun Dependency.isPlugin() = platforms.isEmpty()
fun Dependency.isCommon() = platforms == AllPlatforms

val Dependency.catalogAccessor get() = catalogName.replace("-", ".")
val Dependency.libraryNotation get() = "implementation(libs.$catalogAccessor)"
val Dependency.pluginNotation get() = "alias(libs.plugins.$catalogAccessor)"

interface ProjectFile {
    val path: String
    val content: String
}
