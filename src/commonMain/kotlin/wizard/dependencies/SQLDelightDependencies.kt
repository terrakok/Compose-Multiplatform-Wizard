package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val SQLDelightPlugin = Dependency(
    title = "SQLDelight",
    description = "Generates typesafe Kotlin APIs from SQL.",
    url = "https://github.com/cashapp/sqldelight/",
    group = "app.cash.sqldelight",
    id = "gradle-plugin",
    version = "2.1.0",
    catalogVersionName = "sqlDelight",
    catalogName = "sqlDelight",
    platforms = emptySet()
)

val SQLDelightDriverJvm = SQLDelightPlugin.copy(
    id = "sqlite-driver",
    catalogName = "sqlDelight-driver-sqlite",
    platforms = setOf(Jvm)
)

val SQLDelightDriverAndroid = SQLDelightPlugin.copy(
    id = "android-driver",
    catalogName = "sqlDelight-driver-android",
    platforms = setOf(Android)
)

val SQLDelightDriverNative = SQLDelightPlugin.copy(
    id = "native-driver",
    catalogName = "sqlDelight-driver-native",
    platforms = setOf(Ios, Macos, Linux, Mingw)
)

val SQLDelightDriverJs = SQLDelightPlugin.copy(
    id = "web-worker-driver",
    catalogName = "sqlDelight-driver-js",
    platforms = setOf(Js)
)
