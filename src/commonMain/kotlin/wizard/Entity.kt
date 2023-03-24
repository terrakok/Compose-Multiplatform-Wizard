package wizard

enum class ComposePlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Desktop("Desktop"),
}

val AllPlatforms = setOf(ComposePlatform.Android, ComposePlatform.Ios, ComposePlatform.Desktop)
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
    val composeVersion: String = "1.4.0-alpha01-dev980",
    val dependencies: Set<Dependency> = requiredAndroidDependencies
)

val ProjectInfo.hasAndroid get() = platforms.any { it == ComposePlatform.Android }
val ProjectInfo.hasIos get() = platforms.any { it == ComposePlatform.Ios }
val ProjectInfo.hasDesktop get() = platforms.any { it == ComposePlatform.Desktop }
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")

data class Dependency(
    val title: String,
    val description: String,
    val url: String,
    val group: String,
    val id: String,
    val version: String,
    val platforms: Set<ComposePlatform>
)

fun Dependency.isPlugin() = platforms.isEmpty()
fun Dependency.isCommon() = platforms == AllPlatforms
fun Dependency.libraryNotation() = "implementation(\"$group:$id:$version\")"
fun Dependency.pluginNotation() = when {
    this == KotlinxSerializationPlugin -> "kotlin(\"plugin.serialization\")"
    else -> "id(\"$group\")"
}

interface ProjectFile {
    val path: String
    val content: String
}