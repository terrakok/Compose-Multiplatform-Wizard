package wizard.dependencies

import wizard.Dependency
import wizard.GradleModule
import wizard.ProjectPlatform.*

val KotlinMultiplatformPlugin = Dependency(
    title = "Kotlin Multiplatform Plugin",
    description = "Kotlin gradle plugin.",
    url = "https://github.com/JetBrains/kotlin/",
    group = "org.jetbrains.kotlin.multiplatform",
    id = "gradle-plugin",
    version = "2.2.21",
    catalogVersionName = "kotlin",
    catalogName = "kotlin-multiplatform",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED, GradleModule.WEB)
)

val KotlinJvmPlugin = KotlinMultiplatformPlugin.copy(
    group = "org.jetbrains.kotlin.jvm",
    catalogVersionName = "kotlin",
    catalogName = "kotlin-jvm",
    platforms = setOf(Jvm),
    modules = setOf(GradleModule.DESKTOP)
)

val KotlinAndroidPlugin = KotlinMultiplatformPlugin.copy(
    group = "org.jetbrains.kotlin.android",
    catalogVersionName = "kotlin",
    catalogName = "kotlin-android",
    platforms = setOf(Android),
    modules = setOf(GradleModule.ANDROID)
)

val AndroidApplicationPlugin = Dependency(
    title = "Android Application Plugin",
    description = "Android gradle plugin.",
    url = "https://developer.android.com/studio/build",
    group = "com.android.application",
    id = "gradle-plugin",
    version = "8.12.3",
    catalogVersionName = "agp",
    catalogName = "android-application",
    platforms = setOf(Android),
    modules = setOf(GradleModule.ANDROID)
)

val AndroidKmpLibraryPlugin = AndroidApplicationPlugin.copy(
    group = "com.android.kotlin.multiplatform.library",
    catalogName = "android-kmp-library",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val ComposeMultiplatformPlugin = Dependency(
    title = "Compose Multiplatform",
    description = "An open-source, declarative framework for sharing stunning UIs across multiple platforms.",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose",
    id = "gradle-plugin",
    version = "1.9.1",
    catalogVersionName = "compose-multiplatform",
    catalogName = "compose-multiplatform",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED, GradleModule.WEB, GradleModule.DESKTOP)
)

val ComposeCompilerPlugin = KotlinMultiplatformPlugin.copy(
    title = "Compose Compiler Plugin",
    description = "Compose compiler plugin.",
    url = "https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.compose",
    group = "org.jetbrains.kotlin.plugin.compose",
    id = "gradle-plugin",
    catalogName = "compose-compiler",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED, GradleModule.WEB, GradleModule.ANDROID, GradleModule.DESKTOP)
)

val MavenPublishPlugin = Dependency(
    title = " Gradle Maven Publish Plugin",
    description = " Gradle Maven Publish Plugin.",
    url = "https://github.com/vanniktech/gradle-maven-publish-plugin",
    group = "com.vanniktech.maven.publish",
    id = "gradle-plugin",
    version = "0.34.0",
    catalogVersionName = "maven-publish",
    catalogName = "maven-publish",
    platforms = emptySet()
)

val ComposeHotReloadPlugin = Dependency(
    title = "Compose Hot Reload",
    description = "Compose Hot Reload runs your application on a special desktop JVM, and intelligently reloads your code whenever it is changed.",
    url = "https://github.com/JetBrains/compose-hot-reload",
    group = "org.jetbrains.compose.hot-reload",
    id = "gradle-plugin",
    version = "1.0.0-rc02",
    catalogVersionName = "compose-hot-reload",
    catalogName = "compose-hot-reload",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED, GradleModule.DESKTOP)
)

val AndroidxActivityCompose = Dependency(
    title = "Activity Compose",
    description = "Androidx Activity Compose",
    url = "https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary",
    group = "androidx.activity",
    id = "activity-compose",
    version = "1.10.1",
    catalogVersionName = "androidx-activityCompose",
    catalogName = "androidx-activityCompose",
    platforms = setOf(Android),
    modules = setOf(GradleModule.ANDROID)
)

val AndroidxJUnit4 = Dependency(
    title = "Androidx JUnit4",
    description = "Androidx JUnit4",
    url = "https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary",
    group = "androidx.compose.ui",
    id = "ui-test-junit4",
    version = "1.8.1",
    catalogVersionName = "androidx-uiTest",
    catalogName = "androidx-uitest-junit4",
    platforms = setOf(Android),
    isTestDependency = true
)

val AndroidxTestManifest = AndroidxJUnit4.copy(
    title = "Androidx Test Manifest",
    description = "Androidx Test Manifest",
    id = "ui-test-manifest",
    catalogName = "androidx-uitest-testManifest",
)

val AndroidxLifecycleRuntime = Dependency(
    title = "Androidx Lifecycle Runtime",
    description = "The Lifecycle library offers built-in APIs that let you integrate with Compose Multiplatform.",
    url = "https://developer.android.com/topic/libraries/architecture/lifecycle",
    group = "org.jetbrains.androidx.lifecycle",
    id = "lifecycle-runtime-compose",
    version = "2.9.5",
    catalogVersionName = "androidx-lifecycle",
    catalogName = "androidx-lifecycle-runtime",
    platforms = emptySet()
)

val AndroidxLifecycleViewmodel = AndroidxLifecycleRuntime.copy(
    title = "Androidx Viewmodel",
    description = "The ViewModel class is a business logic or screen level state holder.",
    url = "https://developer.android.com/topic/libraries/architecture/viewmodel",
    id = "lifecycle-viewmodel-compose",
    catalogName = "androidx-lifecycle-viewmodel",
    platforms = emptySet()
)

val AndroidxNavigation = Dependency(
    title = "Androidx Navigation",
    description = "Navigation is a framework for navigating between 'destinations' within an Compose multiplatform application",
    url = "https://developer.android.com/guide/navigation",
    group = "org.jetbrains.androidx.navigation",
    id = "navigation-compose",
    version = "2.9.1",
    catalogVersionName = "androidx-navigation",
    catalogName = "androidx-navigation-compose",
    platforms = emptySet()
)
