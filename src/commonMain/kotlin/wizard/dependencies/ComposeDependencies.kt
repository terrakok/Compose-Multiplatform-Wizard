package wizard.dependencies

import wizard.Dependency
import wizard.GradleModule
import wizard.ProjectPlatform

//https://github.com/JetBrains/compose-multiplatform/blob/master/CHANGELOG.md
val ComposeMultiplatformPlugin = Dependency(
    title = "Compose Multiplatform",
    description = "An open-source, declarative framework for sharing stunning UIs across multiple platforms.",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose",
    id = "gradle-plugin",
    version = "1.10.0-rc01",
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

val ComposeRuntime = Dependency(
    title = "Compose Runtime",
    description = "Compose Runtime",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.runtime",
    id = "runtime",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-runtime",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val ComposeUi = Dependency(
    title = "Compose UI",
    description = "Compose UI",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.ui",
    id = "ui",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-ui",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED, GradleModule.WEB, GradleModule.DESKTOP)
)

val ComposeFoundation = Dependency(
    title = "Compose Foundation",
    description = "Compose Foundation",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.foundation",
    id = "foundation",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-foundation",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val ComposeResources = Dependency(
    title = "Compose Resources",
    description = "Compose Resources",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.components",
    id = "components-resources",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-resources",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val ComposeUiToolingPreview = Dependency(
    title = "Compose UI Tooling Preview",
    description = "Compose UI Tooling Preview",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.ui",
    id = "ui-tooling-preview",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-ui-tooling-preview",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val ComposeUiTooling = Dependency(
    title = "Compose UI Tooling",
    description = "Compose UI Tooling",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.ui",
    id = "ui-tooling",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-ui-tooling",
    platforms = setOf(ProjectPlatform.Android),
    modules = setOf(GradleModule.SHARED)
)

val ComposeUiTest = Dependency(
    title = "Compose UI Test",
    description = "Compose DesktopUI Test",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.ui",
    id = "ui-test",
    version = ComposeMultiplatformPlugin.version,
    catalogVersionName = ComposeMultiplatformPlugin.catalogVersionName,
    catalogName = "compose-ui-test",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED),
    isTestDependency = true
)

val ComposeMaterial3 = Dependency(
    title = "Compose Material3",
    description = "Compose Material3",
    url = "https://www.jetbrains.com/lp/compose-multiplatform/",
    group = "org.jetbrains.compose.material3",
    id = "material3",
    version = "1.10.0-alpha05",
    catalogVersionName = "material3",
    catalogName = "compose-material3",
    platforms = emptySet(),
    modules = setOf(GradleModule.SHARED)
)

val DefaultComposeLibraries = listOf(
    ComposeRuntime,
    ComposeUi,
    ComposeFoundation,
    ComposeResources,
    ComposeUiToolingPreview,
    ComposeUiTooling,
    ComposeUiTest,
    ComposeMaterial3
)