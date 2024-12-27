package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinPlugin = Dependency(
    title = "Kotlin Multiplatform Plugin",
    description = "Kotlin gradle plugin.",
    url = "https://github.com/JetBrains/kotlin/",
    group = "org.jetbrains.kotlin.multiplatform",
    id = "gradle-plugin",
    version = "2.1.0",
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
    version = "8.6.1",
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
    version = "1.7.3",
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
    version = "1.9.3",
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
    version = "1.7.4",
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
    version = "1.7.4",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-uitest-junit4",
    platforms = setOf(Android),
    isTestDependency = true
)

val AndroidxLifecycleRuntime = Dependency(
    title = "Androidx Lifecycle Runtime",
    description = "The Lifecycle library offers built-in APIs that let you integrate with Compose Multiplatform.",
    url = "https://developer.android.com/topic/libraries/architecture/lifecycle",
    group = "org.jetbrains.androidx.lifecycle",
    id = "lifecycle-runtime-compose",
    version = "2.8.4",
    catalogVersionName = "androidx-lifecycle",
    catalogName = "androidx-lifecycle-runtime-compose",
    platforms = emptySet()
)

val AndroidxLifecycleViewmodel = AndroidxLifecycleRuntime.copy(
    title = "Androidx Viewmodel",
    description = "The ViewModel class is a business logic or screen level state holder.",
    url = "https://developer.android.com/topic/libraries/architecture/viewmodel",
    id = "lifecycle-viewmodel",
    catalogName = "androidx-lifecycle-viewmodel",
    platforms = emptySet()
)

val AndroidxNavigation = Dependency(
    title = "Androidx Navigation",
    description = "Navigation is a framework for navigating between 'destinations' within an Compose multiplatform application",
    url = "https://developer.android.com/guide/navigation",
    group = "org.jetbrains.androidx.navigation",
    id = "navigation-compose",
    version = "2.8.0-alpha10",
    catalogVersionName = "androidx-navigation",
    catalogName = "androidx-navigation-composee",
    platforms = emptySet()
)
