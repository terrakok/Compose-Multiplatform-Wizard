package wizard.dependencies

import wizard.Dependency
import wizard.ProjectPlatform.*

val RoomPlugin = Dependency(
    title = "Room",
    description = "Generates SQL By Room",
    url = "https://developer.android.com/kotlin/multiplatform/room",
    group = "androidx.room",
    id = "gradle-plugin",
    version = "2.7.0-alpha03",
    catalogVersionName = "room",
    catalogName = "room",
    platforms = emptySet()
)

val RoomPluginRuntime = RoomPlugin.copy(
    id = "room-runtime",
    catalogName = "room-runtime",
    platforms = setOf()
)

val RoomPluginCompiler= RoomPlugin.copy(
    id = "room-compiler",
    catalogName = "room-compiler",
    platforms = setOf()
)

val DevToolKSP = Dependency(
    title = "KSP",
    description = "Kotlin Symbol Processing (KSP) is an API",
    url = "https://github.com/google/ksp",
    group = "com.google.devtools.ksp",
    id = "gradle-plugin",
    version = "2.0.0-1.0.21",
    catalogVersionName = "ksp",
    catalogName = "ksp",
    platforms = emptySet()
)