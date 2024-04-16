package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinPlugin = Dependency(
    title = "Kotlin Multiplatform Plugin",
    description = "Kotlin gradle plugin.",
    url = "https://github.com/JetBrains/kotlin/",
    group = "org.jetbrains.kotlin.multiplatform",
    id = "gradle-plugin",
    version = "1.9.23",
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
    version = "8.2.2",
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
    version = "1.6.1",
    catalogVersionName = "compose",
    catalogName = "compose",
    platforms = emptySet()
)

val AndroidxActivityCompose = Dependency(
    title = "Activity Compose",
    description = "Androidx Activity Compose",
    url = "https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary",
    group = "androidx.activity",
    id = "activity-compose",
    version = "1.8.2",
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
    version = "1.6.4",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-testManifest",
    platforms = setOf(Android),
    isTestDependency = true
)

val AndroidxJUnit4 = Dependency(
    title = "Androidx JUnit4",
    description = "Androidx JUnit4",
    url = "https://developer.android.com/reference/kotlin/androidx/compose/ui/test/junit4/package-summary",
    group = "androidx.compose.ui",
    id = "ui-test-junit4",
    version = "1.6.4",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-junit4",
    platforms = setOf(Android),
    isTestDependency = true
)
