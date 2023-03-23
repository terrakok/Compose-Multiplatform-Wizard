package wizard

enum class ComposePlatform(val title: String) {
    Android("Android"),
    Ios("iOS"),
    Desktop("Desktop"),
}

data class ProjectInfo(
    val packageId: String = "org.company.app",
    val name: String = "Compose App",
    val platforms: Set<ComposePlatform> = setOf(ComposePlatform.Android, ComposePlatform.Ios, ComposePlatform.Desktop),
    val gradleVersion: String = "8.0.2",
    val kotlinVersion: String = "1.8.10",
    val agpVersion: String = "7.4.2",
    val androidMinSdk: Int = 21,
    val androidTargetSdk: Int = 33,
    val composeVersion: String = "1.4.0-alpha01-dev980",
)

val ProjectInfo.hasAndroid get() = platforms.any { it == ComposePlatform.Android }
val ProjectInfo.hasIos get() = platforms.any { it == ComposePlatform.Ios }
val ProjectInfo.hasDesktop get() = platforms.any { it == ComposePlatform.Desktop }
val ProjectInfo.packagePath get() = packageId.replace(".", "/")
val ProjectInfo.safeName get() = name.replace(" ", "-")

data class Dependency(
    val platforms: Set<ComposePlatform>,
    val title: String,
    val description: String,
    val version: String,
    val id: String
)

interface ProjectFile {
    val path: String
    val content: String
}