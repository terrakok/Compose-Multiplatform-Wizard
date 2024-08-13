package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinPlugin = Dependency(
    title = "Kotlin Multiplatform Plugin",
    description = "Kotlin gradle plugin.",
    url = "https://github.com/JetBrains/kotlin/",
    group = "org.jetbrains.kotlin.multiplatform",
    id = "gradle-plugin",
    version = "2.0.20-RC",
    catalogVersionName = "kotlin",
    catalogName = "multiplatform",
    platforms = emptySet()
)

val AndroidApplicationPlugin = Dependency(
    title = "Android Application Plugin",
    description = "Android gradle plugin.",
    url = "https://developer.android.com/studio/build",
    group = "com.android.application",
    id = "gradle-plugin",
    version = "8.5.2",
    catalogVersionName = "agp",
    catalogName = "android-application",
    platforms = emptySet()
)

val AndroidLibraryPlugin = AndroidApplicationPlugin.copy(
    title = "Android Library Plugin",
    group = "com.android.library",
    catalogName = "android-library",
    platforms = emptySet()
)

val ComposePlugin = Dependency(
    title = "Compose Multiplatform Plugin",
    description = "Compose gradle plugin.",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose",
    id = "gradle-plugin",
    version = "1.7.0-alpha02",
    catalogVersionName = "compose",
    catalogName = "compose",
    platforms = emptySet()
)

val ComposeCompilerPlugin = KotlinPlugin.copy(
    title = "Compose Compiler Plugin",
    description = "Compose compiler plugin.",
    url = "https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.compose",
    group = "org.jetbrains.kotlin.plugin.compose",
    id = "gradle-plugin",
    catalogName = "compose-compiler",
    platforms = emptySet()
)

val AndroidxActivityCompose = Dependency(
    title = "Activity Compose",
    description = "Androidx Activity Compose",
    url = "https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary",
    group = "androidx.activity",
    id = "activity-compose",
    version = "1.9.1",
    catalogVersionName = "androidx-activityCompose",
    catalogName = "androidx-activityCompose",
    platforms = setOf(Android)
)

val AndroidxTestManifest = Dependency(
    title = "Androidx Test Manifest",
    description = "Androidx Test Manifest",
    url = "https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary",
    group = "androidx.compose.ui",
    id = "ui-test-manifest",
    version = "1.6.8",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-uitest-testManifest",
    platforms = setOf(Android),
    isTestDependency = true
)

val AndroidxJUnit4 = Dependency(
    title = "Androidx JUnit4",
    description = "Androidx JUnit4",
    url = "https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/package-summary",
    group = "androidx.compose.ui",
    id = "ui-test-junit4",
    version = "1.6.8",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-uitest-junit4",
    platforms = setOf(Android),
    isTestDependency = true
)
