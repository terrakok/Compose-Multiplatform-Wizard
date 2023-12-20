package wizard

val AndroidxAppcompat = Dependency(
    title = "Appcompat",
    description = "Androidx Appcompat",
    url = "https://developer.android.com/jetpack/androidx/releases/appcompat",
    group = "androidx.appcompat",
    id = "appcompat",
    version = "1.6.1",
    catalogVersionName = "androidx-appcompat",
    catalogName = "androidx-appcompat",
    platforms = setOf(ComposePlatform.Android)
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
    platforms = setOf(ComposePlatform.Android)
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
    platforms = setOf(ComposePlatform.Android)
)

val Voyager = Dependency(
    title = "Voyager",
    description = "A pragmatic navigation library for Compose.",
    url = "https://github.com/adrielcafe/voyager",
    group = "cafe.adriel.voyager",
    id = "voyager-navigator",
    version = "1.0.0",
    catalogVersionName = "voyager",
    catalogName = "voyager-navigator",
    platforms = AllPlatforms
)

val ImageLoader = Dependency(
    title = "Compose ImageLoader",
    description = "Compose Image library for Kotlin Multiplatform.",
    url = "https://github.com/qdsfdhvh/compose-imageloader",
    group = "io.github.qdsfdhvh",
    id = "image-loader",
    version = "1.7.1",
    catalogVersionName = "composeImageLoader",
    catalogName = "composeImageLoader",
    platforms = AllPlatforms
)

val Napier = Dependency(
    title = "Napier",
    description = "Napier is a logger library for Kotlin Multiplatform.",
    url = "https://github.com/AAkira/Napier",
    group = "io.github.aakira",
    id = "napier",
    version = "2.6.1",
    catalogVersionName = "napier",
    catalogName = "napier",
    platforms = AllPlatforms
)

val Kermit = Dependency(
    title = "Kermit",
    description = "Kermit is a logger library for Kotlin Multiplatform.",
    url = "https://github.com/touchlab/Kermit",
    group = "co.touchlab",
    id = "kermit",
    version = "2.0.0-RC4",
    catalogVersionName = "kermit",
    catalogName = "kermit",
    platforms = AllPlatforms
)

val KotlinxDateTime = Dependency(
    title = "Kotlinx DateTime",
    description = "A multiplatform Kotlin library for working with date and time.",
    url = "https://github.com/Kotlin/kotlinx-datetime",
    group = "org.jetbrains.kotlinx",
    id = "kotlinx-datetime",
    version = "0.5.0",
    catalogVersionName = "kotlinx-datetime",
    catalogName = "kotlinx-datetime",
    platforms = AllPlatforms
)

val MultiplatformSettings = Dependency(
    title = "Multiplatform Settings",
    description = "A Kotlin Multiplatform library for saving simple key-value data.",
    url = "https://github.com/russhwolf/multiplatform-settings",
    group = "com.russhwolf",
    id = "multiplatform-settings",
    version = "1.1.1",
    catalogVersionName = "multiplatformSettings",
    catalogName = "multiplatformSettings",
    platforms = AllPlatforms
)

val Koin = Dependency(
    title = "Koin",
    description = "A pragmatic lightweight dependency injection framework for Kotlin & Kotlin Multiplatform.",
    url = "https://github.com/InsertKoinIO/koin",
    group = "io.insert-koin",
    id = "koin-core",
    version = "3.5.0",
    catalogVersionName = "koin",
    catalogName = "koin-core",
    platforms = AllPlatforms
)

val Kodein = Dependency(
    title = "Kodein",
    description = "KODEIN is a very simple and yet very useful dependency retrieval container.",
    url = "https://github.com/kosi-libs/Kodein",
    group = "org.kodein.di",
    id = "kodein-di",
    version = "7.19.0",
    catalogVersionName = "kodein",
    catalogName = "kodein",
    platforms = AllPlatforms
)

val KStore = Dependency(
    title = "KStore",
    description = "A tiny Kotlin multiplatform library that assists in saving and restoring objects to and from disk.",
    url = "https://github.com/xxfast/KStore",
    group = "io.github.xxfast",
    id = "kstore",
    version = "0.7.1",
    catalogVersionName = "kstore",
    catalogName = "kstore",
    platforms = AllPlatforms
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
    platforms = AllPlatforms
)

val KtorClientDarwin = KtorCore.copy(
    id = "ktor-client-darwin",
    catalogName = "ktor-client-darwin",
    platforms = setOf(ComposePlatform.Ios)
)

val KtorClientOkhttp = KtorCore.copy(
    id = "ktor-client-okhttp",
    catalogName = "ktor-client-okhttp",
    platforms = setOf(ComposePlatform.Android, ComposePlatform.Desktop)
)

val KtorClientJs = KtorCore.copy(
    id = "ktor-client-js",
    catalogName = "ktor-client-js",
    platforms = setOf(ComposePlatform.Browser)
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
    platforms = AllPlatforms
)

val MokoMvvm = Dependency(
    title = "Moko MVVM",
    description = "This is a Kotlin Multiplatform library that provides architecture components of Model-View-ViewModel for UI applications.",
    url = "https://github.com/icerockdev/moko-mvvm",
    group = "dev.icerock.moko",
    id = "mvvm-compose",
    version = "0.16.1",
    catalogVersionName = "moko-mvvm",
    catalogName = "moko-mvvm",
    platforms = AllPlatforms
)

val KotlinxCoroutinesAndroid = KotlinxCoroutinesCore.copy(
    id = "kotlinx-coroutines-android",
    catalogName = "kotlinx-coroutines-android",
    platforms = setOf(ComposePlatform.Android)
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
    platforms = AllPlatforms
)

val SQLDelightPlugin = Dependency(
    title = "SQLDelight",
    description = "Generates typesafe Kotlin APIs from SQL.",
    url = "https://github.com/cashapp/sqldelight/",
    group = "app.cash.sqldelight",
    id = "gradle-plugin",
    version = "2.0.1",
    catalogVersionName = "sqlDelight",
    catalogName = "sqlDelight",
    platforms = emptySet()
)

val SQLDelightDriverJvm = SQLDelightPlugin.copy(
    id = "sqlite-driver",
    catalogName = "sqlDelight-driver-sqlite",
    platforms = setOf(ComposePlatform.Desktop)
)

val SQLDelightDriverAndroid = SQLDelightPlugin.copy(
    id = "android-driver",
    catalogName = "sqlDelight-driver-android",
    platforms = setOf(ComposePlatform.Android)
)

val SQLDelightDriverNative = SQLDelightPlugin.copy(
    id = "native-driver",
    catalogName = "sqlDelight-driver-native",
    platforms = setOf(ComposePlatform.Ios)
)

val SQLDelightDriverJs = SQLDelightPlugin.copy(
    id = "web-worker-driver",
    catalogName = "sqlDelight-driver-js",
    platforms = setOf(ComposePlatform.Browser)
)

val ApolloPlugin = Dependency(
    title = "Apollo Kotlin",
    description = "Generates typesafe Kotlin APIs from your GraphQL queries.",
    url = "https://github.com/apollographql/apollo-kotlin/",
    group = "com.apollographql.apollo3",
    id = "apollo-gradle-plugin",
    version = "4.0.0-beta.4",
    catalogVersionName = "apollo",
    catalogName = "apollo",
    platforms = emptySet()
)

val ApolloRuntime = ApolloPlugin.copy(
    id = "apollo-runtime",
    catalogName = "apollo-runtime",
    platforms = AllPlatforms
)

val BuildConfigPlugin = Dependency(
    title = "Build Config",
    description = "A plugin for generating BuildConstants.",
    url = "https://github.com/gmazzo/gradle-buildconfig-plugin",
    group = "com.github.gmazzo.buildconfig",
    id = "gradle-plugin",
    version = "4.1.1",
    catalogVersionName = "buildConfig",
    catalogName = "buildConfig",
    platforms = emptySet()
)

val BuildKonfigPlugin = Dependency(
    title = "BuildKonfig",
    description = "BuildConfig for Kotlin Multiplatform Project.",
    url = "https://github.com/yshrsmz/BuildKonfig",
    group = "com.codingfeline.buildkonfig",
    id = "buildkonfig-gradle-plugin",
    version = "0.13.3",
    catalogVersionName = "buildKonfig",
    catalogName = "buildKonfig",
    platforms = emptySet()
)
