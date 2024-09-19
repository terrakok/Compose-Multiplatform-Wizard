package ui

import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.Stack
import mui.system.Container
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.onChange
import web.cssom.*
import web.html.HTMLInputElement
import wizard.DefaultComposeAppInfo
import wizard.ProjectPlatform
import wizard.ProjectPlatform.*
import wizard.dependencies.*
import mui.icons.material.Android as AndroidIcon

val ComposeAppWizardContent = FC<AppProps> { props ->
    Container {
        sx {
            padding = Padding(24.px, 24.px)
            minWidth = 1000.px
        }

        ShowVersionContext.Provider {
            value = useState(false)

            Paper {
                sx {
                    padding = Padding(24.px, 24.px)
                }

                TopMenu()

                Stack {
                    direction = responsive(StackDirection.column)
                    spacing = responsive(2)
                    sx {
                        alignItems = AlignItems.center
                    }

                    Header {
                        image = "compose-logo.svg"
                        title = "Compose Multiplatform Wizard"
                    }

                    val default = DefaultComposeAppInfo()
                    val textFieldWidth = 565.px

                    var projectName by useState(default.name)
                    TextField {
                        label = ReactNode("Project name")
                        sx {
                            width = textFieldWidth
                        }
                        value = projectName
                        onChange = { event ->
                            projectName = (event.target as HTMLInputElement).value
                        }
                    }

                    var projectId by useState(default.packageId)
                    TextField {
                        label = ReactNode("Project ID")
                        sx {
                            width = textFieldWidth
                        }
                        value = projectId
                        onChange = { event ->
                            projectId = (event.target as HTMLInputElement).value
                        }
                    }

                    var platforms by useState(setOf(Android, Ios, Jvm, Wasm))
                    fun switch(platform: ProjectPlatform) {
                        platforms = if (platforms.contains(platform)) {
                            platforms - platform
                        } else {
                            platforms + platform
                        }
                    }

                    ButtonGroup {
                        disableElevation = true
                        TargetButton {
                            title = "Android"
                            isSelected = platforms.contains(Android)
                            onClick = { switch(Android) }
                            icon = AndroidIcon
                        }
                        TargetButton {
                            title = "iOS"
                            isSelected = platforms.contains(Ios)
                            onClick = { switch(Ios) }
                            icon = Apple
                        }
                        TargetButton {
                            title = "Desktop"
                            isSelected = platforms.contains(Jvm)
                            onClick = { switch(Jvm) }
                            icon = Laptop
                        }
                        TargetButton {
                            title = "Browser (Wasm)"
                            isSelected = platforms.contains(Wasm)
                            onClick = { switch(Wasm) }
                            icon = Language
                            status = "Alpha (some libraries don't support it yet)"
                        }
                        TargetButton {
                            title = "Browser (JS)"
                            isSelected = platforms.contains(Js)
                            onClick = { switch(Js) }
                            icon = Language
                            status = "Experimental"
                        }
                    }

                    VersionsTable {
                        sx {
                            width = textFieldWidth
                        }
                        info = default
                    }

                    val deps = setOf(
                        DependencyBox(listOf(Kermit, Napier), false),
                        DependencyBox(KotlinxCoroutinesCore, false),
                        DependencyBox(KtorCore, false),
                        DependencyBox(AndroidxLifecycleViewmodel, false),
                        DependencyBox(listOf(AndroidxNavigation, Voyager, Decompose, PreCompose), false),
                        DependencyBox(KotlinxSerializationJson, false),
                        DependencyBox(listOf(Koin, Kodein), false),
                        DependencyBox(listOf(Coil, ImageLoader), false),
                        DependencyBox(MultiplatformSettings, false),
                        DependencyBox(KotlinxDateTime, false),
                        DependencyBox(listOf(SQLDelightPlugin, RoomPlugin), false),
                        DependencyBox(ApolloPlugin, false),
                        DependencyBox(KStore, false),
                        DependencyBox(listOf(BuildConfigPlugin, BuildKonfigPlugin), false),
                        DependencyBox(
                            listOf(
                                ComposeIconsFeather,
                                ComposeIconsFontAwesome,
                                ComposeIconsSimple,
                                ComposeIconsTabler,
                                ComposeIconsEva,
                                ComposeIconsOcticons,
                                ComposeIconsLinea,
                                ComposeIconsLineAwesome,
                                ComposeIconsWeather,
                                ComposeIconsCSSGG
                            ), false
                        ),
                    )
                    Grid {
                        sx {
                            justifyContent = JustifyContent.spaceAround
                        }
                        spacing = responsive(2)
                        container = true
                        deps.forEach { dep ->
                            Grid {
                                item = true
                                DependencyCard {
                                    dependency = dep
                                }
                            }
                        }
                    }

                    Button {
                        variant = ButtonVariant.contained
                        size = Size.large
                        startIcon = ArrowCircleDown.create()
                        +"Download"

                        disabled = projectName.isBlank()
                                || projectId.isBlank()
                                || platforms.isEmpty()

                        onClick = {
                            val info = default.copy(
                                packageId = projectId,
                                name = projectName,
                                platforms = platforms,
                                dependencies = buildSet {
                                    add(KotlinPlugin)
                                    add(ComposeCompilerPlugin)
                                    add(ComposePlugin)
                                    if (platforms.contains(Android)) {
                                        add(AndroidApplicationPlugin)
                                        add(AndroidxActivityCompose)
                                        add(AndroidxTestManifest)
                                        add(AndroidxJUnit4)
                                    }
                                    addAll(deps.getSelectedDependencies())
                                }
                            )
                            props.generate(info)
                        }
                    }
                }
            }
        }
    }
}

internal fun Set<DependencyBox>.getSelectedDependencies() =
    this
        .filter { it.isSelected.component1() }
        .map { it.selectedDep.component1() }
        .flatMap {
            when (it) {
                KtorCore -> listOfNotNull(
                    KtorCore,
                    KtorClientContentNegotiation,
                    KtorClientSerialization,
                    KtorClientLogging,
                    KtorClientDarwin,
                    KtorClientOkhttp,
                    KtorClientJs,
                    KtorClientLinux,
                    KtorClientMingw
                )
                SQLDelightPlugin -> listOf(
                    SQLDelightPlugin,
                    SQLDelightDriverJvm,
                    SQLDelightDriverAndroid,
                    SQLDelightDriverNative,
                    SQLDelightDriverJs
                )
                RoomPlugin -> listOf(
                    RoomPlugin,
                    RoomPluginRuntime,
                    RoomPluginCompiler,
                    DevToolKSP
                )
                Koin -> listOf(Koin, KoinCompose)
                Coil -> listOf(Coil, CoilNetwork)
                Decompose -> listOf(Decompose, DecomposeCompose)
                ApolloPlugin -> listOf(ApolloPlugin, ApolloRuntime)
                AndroidxLifecycleViewmodel -> listOf(AndroidxLifecycleViewmodel, AndroidxLifecycleRuntime)
                KotlinxCoroutinesCore -> listOf(
                    KotlinxCoroutinesCore,
                    KotlinxCoroutinesAndroid,
                    KotlinxCoroutinesJvm,
                    KotlinxCoroutinesTest
                )
                KotlinxSerializationJson -> listOf(KotlinxSerializationPlugin, KotlinxSerializationJson)
                else -> listOf(it)
            }
        }
        .toSet()
