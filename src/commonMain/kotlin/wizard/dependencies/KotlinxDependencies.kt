package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val KotlinxDateTime = Dependency(
    title = "Kotlinx DateTime",
    description = "A multiplatform Kotlin library for working with date and time.",
    url = "https://github.com/Kotlin/kotlinx-datetime",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-datetime",
    version = "0.5.0",
    catalogVersionName = "kotlinx-datetime",
    catalogName = "kotlinx-datetime",
    platforms = emptySet()
)

val KtorCore = Dependency(
    title = "Ktor client",
    description = "A multiplatform asynchronous HTTP client, which allows you to make requests and handle responses.",
    url = "https://github.com/ktorio/ktor",
    group = "io.ktor",
    id = "ktor-client-core",
    version = "2.3.7",
    catalogVersionName = "ktor",
    catalogName = "ktor-core",
    platforms = emptySet()
)

val KtorClientDarwin = KtorCore.copy(
    id = "ktor-client-darwin",
    catalogName = "ktor-client-darwin",
    platforms = setOf(Ios)
)

val KtorClientOkhttp = KtorCore.copy(
    id = "ktor-client-okhttp",
    catalogName = "ktor-client-okhttp",
    platforms = setOf(Android, Jvm)
)

val KtorClientJs = KtorCore.copy(
    id = "ktor-client-js",
    catalogName = "ktor-client-js",
    platforms = setOf(Js)
)

val KotlinxCoroutinesCore = Dependency(
    title = "Kotlinx Coroutines",
    description = "Library support for Kotlin coroutines with multiplatform support.",
    url = "https://github.com/Kotlin/kotlinx.coroutines",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-coroutines-core",
    version = "1.7.3",
    catalogVersionName = "kotlinx-coroutines",
    catalogName = "kotlinx-coroutines-core",
    platforms = emptySet()
)

val KotlinxCoroutinesAndroid = KotlinxCoroutinesCore.copy(
    id = "kotlinx-coroutines-android",
    catalogName = "kotlinx-coroutines-android",
    platforms = setOf(Android)
)

val KotlinxSerializationPlugin = Dependency(
    title = "Kotlinx Serialization",
    description = "Kotlin serialization consists of a compiler plugin.",
    url = "https://github.com/Kotlin/kotlinx.serialization",
    group = "org.jetbrains.kotlin.plugin.serialization",
    id = "gradle-plugin",
    version = "[the same as Kotlin version!]",
    catalogVersionName = "kotlin",
    catalogName = "kotlinx-serialization",
    platforms = emptySet()
)

val KotlinxSerializationJson = KotlinxSerializationPlugin.copy(
    description = "A multiplatform JSON serialization library.",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-serialization-json",
    version = "1.6.2",
    catalogVersionName = "kotlinx-serialization",
    catalogName = "kotlinx-serialization-json",
    platforms = emptySet()
)
