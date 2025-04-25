package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.Companion.composePlatforms

val Voyager = Dependency(
    title = "Voyager",
    description = "A pragmatic navigation library for Compose.",
    url = "https://github.com/adrielcafe/voyager",
    group = "cafe.adriel.voyager",
    id = "voyager-navigator",
    version = "1.1.0-beta03",
    catalogVersionName = "voyager",
    catalogName = "voyager-navigator",
    platforms = emptySet()
)

val Decompose = Dependency(
    title = "Decompose",
    description = "Decompose is a library for breaking down your code into lifecycle-aware components aka BLoC",
    url = "https://github.com/arkivanov/Decompose",
    group = "com.arkivanov.decompose",
    id = "decompose",
    version = "3.3.0",
    catalogVersionName = "decompose",
    catalogName = "decompose",
    platforms = emptySet()
)

val DecomposeCompose = Decompose.copy(
    id = "extensions-compose",
    catalogName = "decompose-compose"
)

val PreCompose = Dependency(
    title = "PreCompose",
    description = "Multiplatform Navigation && ViewModel, inspired by Jetpack Navigation, ViewModel and Lifecycle",
    url = "https://github.com/Tlaster/PreCompose",
    group = "moe.tlaster",
    id = "precompose",
    version = "1.6.2",
    catalogVersionName = "precompose",
    catalogName = "precompose",
    platforms = emptySet()
)

val ImageLoader = Dependency(
    title = "Compose ImageLoader",
    description = "Compose Image library for Kotlin Multiplatform.",
    url = "https://github.com/qdsfdhvh/compose-imageloader",
    group = "io.github.qdsfdhvh",
    id = "image-loader",
    version = "1.10.0",
    catalogVersionName = "composeImageLoader",
    catalogName = "composeImageLoader",
    platforms = emptySet()
)

val Coil = Dependency(
    title = "Coil",
    description = "Image loading for Android and Compose Multiplatform.",
    url = "https://github.com/coil-kt/coil",
    group = "io.coil-kt.coil3",
    id = "coil-compose-core",
    version = "3.1.0",
    catalogVersionName = "coil",
    catalogName = "coil",
    platforms = emptySet()
)

val CoilNetwork = Coil.copy(
    id = "coil-network-ktor3",
    catalogName = "coil-network-ktor",
)

val Napier = Dependency(
    title = "Napier",
    description = "Napier is a logger library for Kotlin Multiplatform.",
    url = "https://github.com/AAkira/Napier",
    group = "io.github.aakira",
    id = "napier",
    version = "2.7.1",
    catalogVersionName = "napier",
    catalogName = "napier",
    platforms = emptySet()
)

val Kermit = Dependency(
    title = "Kermit",
    description = "Kermit is a logger library for Kotlin Multiplatform.",
    url = "https://github.com/touchlab/Kermit",
    group = "co.touchlab",
    id = "kermit",
    version = "2.0.5",
    catalogVersionName = "kermit",
    catalogName = "kermit",
    platforms = emptySet()
)

val KotlinLogging = Dependency(
    title = "Kotlin-Logging",
    description = "Kotlin-Logging is a lightweight logging framework for Kotlin.",
    url = "https://github.com/oshai/kotlin-logging",
    group = "io.github.oshai",
    id = "kotlin-logging",
    version = "7.0.7",
    catalogVersionName = "kotlinLogging",
    catalogName = "kotlinLogging",
    platforms = emptySet()
)

val MultiplatformSettings = Dependency(
    title = "Multiplatform Settings",
    description = "A Kotlin Multiplatform library for saving simple key-value data.",
    url = "https://github.com/russhwolf/multiplatform-settings",
    group = "com.russhwolf",
    id = "multiplatform-settings",
    version = "1.3.0",
    catalogVersionName = "multiplatformSettings",
    catalogName = "multiplatformSettings",
    platforms = emptySet()
)

val KotlinInject = Dependency(
    title = "Kotlin-Inject",
    description = "A compile-time dependency injection library for kotlin.",
    url = "https://github.com/evant/kotlin-inject",
    group = "me.tatarka.inject",
    id = "kotlin-inject-runtime",
    version = "0.7.2",
    catalogVersionName = "kotlinInject",
    catalogName = "kotlinInject",
    platforms = emptySet()
)

val KotlinInjectCompiler = KotlinInject.copy(
    id = "kotlin-inject-compiler-ksp",
    catalogName = "kotlinInjectKsp",
    platforms = composePlatforms.toSet(),
    isKspDependency = true
)

val Koin = Dependency(
    title = "Koin",
    description = "A pragmatic lightweight dependency injection framework for Kotlin & Kotlin Multiplatform.",
    url = "https://github.com/InsertKoinIO/koin",
    group = "io.insert-koin",
    id = "koin-core",
    version = "4.0.4",
    catalogVersionName = "koin",
    catalogName = "koin-core",
    platforms = emptySet()
)

val KoinCompose = Koin.copy(
    id = "koin-compose",
    catalogName = "koin-compose",
)

val Kodein = Dependency(
    title = "Kodein",
    description = "KODEIN is a very simple and yet very useful dependency retrieval container.",
    url = "https://github.com/kosi-libs/Kodein",
    group = "org.kodein.di",
    id = "kodein-di",
    version = "7.25.0",
    catalogVersionName = "kodein",
    catalogName = "kodein",
    platforms = emptySet()
)

val KStore = Dependency(
    title = "KStore",
    description = "A tiny Kotlin multiplatform library that assists in saving and restoring objects to and from disk.",
    url = "https://github.com/xxfast/KStore",
    group = "io.github.xxfast",
    id = "kstore",
    version = "0.9.1",
    catalogVersionName = "kstore",
    catalogName = "kstore",
    platforms = emptySet()
)

val ApolloPlugin = Dependency(
    title = "Apollo Kotlin",
    description = "Generates typesafe Kotlin APIs from your GraphQL queries.",
    url = "https://github.com/apollographql/apollo-kotlin/",
    group = "com.apollographql.apollo",
    id = "gradle-plugin",
    version = "4.1.1",
    catalogVersionName = "apollo",
    catalogName = "apollo",
    platforms = emptySet()
)

val ApolloRuntime = ApolloPlugin.copy(
    id = "apollo-runtime",
    catalogName = "apollo-runtime",
    platforms = emptySet()
)

val BuildConfigPlugin = Dependency(
    title = "Build Config",
    description = "A plugin for generating BuildConstants.",
    url = "https://github.com/gmazzo/gradle-buildconfig-plugin",
    group = "com.github.gmazzo.buildconfig",
    id = "gradle-plugin",
    version = "5.4.0",
    catalogVersionName = "buildConfig",
    catalogName = "buildConfig",
    platforms = emptySet()
)

val BuildKonfigPlugin = Dependency(
    title = "BuildKonfig",
    description = "BuildConfig for Kotlin Multiplatform Project.",
    url = "https://github.com/yshrsmz/BuildKonfig",
    group = "com.codingfeline.buildkonfig",
    id = "gradle-plugin",
    version = "0.15.1",
    catalogVersionName = "buildKonfig",
    catalogName = "buildKonfig",
    platforms = emptySet()
)
