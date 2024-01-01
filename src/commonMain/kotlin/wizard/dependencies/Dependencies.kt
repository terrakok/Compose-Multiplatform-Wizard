package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinPlugin = Dependency(
    title = "Kotlin Multiplatform Plugin",
    description = "Kotlin gradle plugin.",
    url = "https://github.com/JetBrains/kotlin/",
    group = "org.jetbrains.kotlin.multiplatform",
    id = "gradle-plugin",
    version = "1.9.21",
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
    version = "8.2.0",
    catalogVersionName = "agp",
    catalogName = "android-application",
    platforms = emptySet()
)

val ComposePlugin = Dependency(
    title = "Compose Multiplatform Plugin",
    description = "Compose gradle plugin.",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose",
    id = "gradle-plugin",
    version = "1.5.11",
    catalogVersionName = "compose",
    catalogName = "compose",
    platforms = emptySet()
)

val AndroidxAppcompat = Dependency(
    title = "Appcompat",
    description = "Androidx Appcompat",
    url = "https://developer.android.com/jetpack/androidx/releases/appcompat",
    group = "androidx.appcompat",
    id = "appcompat",
    version = "1.6.1",
    catalogVersionName = "androidx-appcompat",
    catalogName = "androidx-appcompat",
    platforms = setOf(Android)
)

val AndroidxActivityCompose = Dependency(
    title = "Activity Compose",
    description = "Androidx Activity Compose",
    url = "https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary",
    group = "androidx.activity",
    id = "activity-compose",
    version = "1.8.1",
    catalogVersionName = "androidx-activityCompose",
    catalogName = "androidx-activityCompose",
    platforms = setOf(Android)
)

val ComposeUiTooling = Dependency(
    title = "Compose UI Tooling",
    description = "Compose UI Tooling",
    url = "https://developer.android.com/jetpack/androidx/releases/compose-ui",
    group = "androidx.compose.ui",
    id = "ui-tooling",
    version = "1.5.4",
    catalogVersionName = "compose-uitooling",
    catalogName = "compose-uitooling",
    platforms = setOf(Android)
)
