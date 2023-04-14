package wizard

enum class ComposePlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Desktop("Desktop"),
    Browser("Browser"),
}

val AllPlatforms = setOf(ComposePlatform.Android, ComposePlatform.Ios, ComposePlatform.Desktop, ComposePlatform.Browser)
val requiredAndroidDependencies = setOf(AndroidxAppcompat, AndroidxActivityCompose, ComposeUiTooling)

data class ProjectInfo(
    val packageId: String = "org.company.app",
    val name: String = "Compose App",
    val platforms: Set<ComposePlatform> = AllPlatforms,
    val gradleVersion: String = "8.1",
    val kotlinVersion: String = "1.8.20",
    val agpVersion: String = "7.4.2",
    val androidMinSdk: Int = 21,
    val androidTargetSdk: Int = 33,
    val composeVersion: String = "1.4.0",
    val dependencies: Set<Dependency> = requiredAndroidDependencies
)

val ProjectInfo.hasAndroid get() = platforms.any { it == ComposePlatform.Android }
val ProjectInfo.hasIos get() = platforms.any { it == ComposePlatform.Ios }
val ProjectInfo.hasDesktop get() = platforms.any { it == ComposePlatform.Desktop }
val ProjectInfo.hasBrowser get() = platforms.any { it == ComposePlatform.Browser }
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")
val ProjectInfo.hasCustomResources get() = dependencies.contains(LibresPlugin)

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