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
    val gradleVersion: String = "8.0.2",
    val kotlinVersion: String = "1.8.10",
    val agpVersion: String = "7.4.2",
    val androidMinSdk: Int = 21,
    val androidTargetSdk: Int = 33,
    val composeVersion: String = "1.4.0-alpha01-dev1004",
    val dependencies: Set<Dependency> = requiredAndroidDependencies
)

val ProjectInfo.hasAndroid get() = platforms.any { it == ComposePlatform.Android }
val ProjectInfo.hasIos get() = platforms.any { it == ComposePlatform.Ios }
val ProjectInfo.hasDesktop get() = platforms.any { it == ComposePlatform.Desktop }
val ProjectInfo.hasBrowser get() = platforms.any { it == ComposePlatform.Browser }
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")

val ProjectInfo.kotlinVersionRef get() = "kotlin"
val ProjectInfo.agpVersionRef get() = "agp"
val ProjectInfo.composeVersionRef get() = "compose"

data class Dependency(
    val title: String,
    val description: String,
    val url: String,
    val group: String,
    val id: String,
    val version: String,
    val versionCatalog: VersionCatalog,
    val applyToModule: Boolean,
    val platforms: Set<ComposePlatform>,
)

data class VersionCatalog(
    val versionRefName: String,
    val libraryName: String,
)

fun Dependency.isPlugin() = platforms.isEmpty()
fun Dependency.isCommon() = platforms == AllPlatforms
fun Dependency.libraryNotation() = "implementation(libs.${versionCatalog.libraryAccessor})"
fun Dependency.pluginNotation() = when {
    this == KotlinxSerializationPlugin -> "kotlin(\"plugin.serialization\")"
    else -> "alias(libs.plugins.${versionCatalog.libraryAccessor})"
}

val VersionCatalog.libraryAccessor: String get() = libraryName.replace("-", ".")

interface ProjectFile {
    val path: String
    val content: String
}