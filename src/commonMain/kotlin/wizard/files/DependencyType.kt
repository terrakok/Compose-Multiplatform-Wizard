package wizard.files

import wizard.ComposeIconsFeather
import wizard.Dependency
import wizard.Kermit
import wizard.LibresCompose
import wizard.Napier
import wizard.*

enum class DependencyType(
    val displayName: String,
    val allowMultipleSelection: Boolean,
    val selectedByDefault: Boolean,
) {
    Logging("Logging", allowMultipleSelection = false, selectedByDefault = true) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                Napier,
                Kermit,
            )
        }
    },
    ResourceGeneration("Resource Generation", allowMultipleSelection = false, selectedByDefault = true) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                LibresCompose,
            )
        }
    },
    Navigation("Navigation", allowMultipleSelection = false, selectedByDefault = true) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                Voyager,
            )
        }
    },
    Icons("Icons", allowMultipleSelection = true, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                ComposeIconsFeather,
                ComposeIconsFontAwesome,
                ComposeIconsSimple,
                ComposeIconsTabler,
                ComposeIconsEva,
                ComposeIconsOcticons,
                ComposeIconsLinea,
                ComposeIconsLineAwesome,
                ComposeIconsWeather,
                ComposeIconsCSSGG,
            )
        }
    },
    BuildConfig("Build Config", allowMultipleSelection = false, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                BuildConfigPlugin,
                BuildKonfigPlugin,
            )
        }
    },
    DependencyInjection("DI", allowMultipleSelection = false, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                Koin,
                Kodein,
            )
        }
    },
    Persistence("Persistence", allowMultipleSelection = true, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                KStore,
                SQLDelightPlugin,
                MultiplatformSettings
            )
        }
    },
    ApiClient("API Client", allowMultipleSelection = true, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                KtorCore,
                ApolloPlugin,
            )
        }
    },
    Other("Other", allowMultipleSelection = true, selectedByDefault = false) {
        override fun getDependencies(): List<Dependency> {
            return listOf(
                ImageLoader,
                KotlinxCoroutinesCore,
                KotlinxSerializationJson,
                KotlinxDateTime,
            )
        }
    };

    open fun getDependencies(): List<Dependency> {
        return emptyList()
    }
}
