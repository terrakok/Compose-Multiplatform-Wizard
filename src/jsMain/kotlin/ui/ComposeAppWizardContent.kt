package ui

import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.Stack
import mui.material.styles.TypographyVariant
import mui.system.Container
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.onChange
import web.cssom.*
import web.html.HTMLInputElement
import web.window.window
import wizard.DefaultComposeAppInfo
import wizard.ProjectPlatform
import wizard.ProjectPlatform.*
import wizard.dependencies.*
import wizard.enableJvmHotReload
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

                    var enableJvmHotReload by useState(default.enableJvmHotReload)
                    if (platforms.contains(Jvm)) {
                        Card {
                            sx {
                                width = textFieldWidth
                            }
                            onClick = {
                                enableJvmHotReload = !enableJvmHotReload
                            }
                            CardActionArea {
                                Stack {
                                    sx {
                                        height = 60.px
                                        marginRight = 8.px
                                        marginLeft = 16.px
                                        alignItems = AlignItems.center
                                        justifyContent = JustifyContent.spaceBetween
                                    }
                                    direction = responsive(StackDirection.row)
                                    spacing = responsive(1)

                                    Stack {
                                        direction = responsive(StackDirection.row)

                                        Typography {
                                            variant = TypographyVariant.subtitle1
                                            +"\uD83D\uDD25 Desktop App Hot Reload"
                                        }
                                        Button {
                                            sx {
                                                marginLeft = 8.px
                                            }
                                            size = Size.small
                                            onClick = {
                                                it.stopPropagation()
                                                window.open("https://github.com/JetBrains/compose-hot-reload")
                                            }
                                            +"info"
                                        }
                                    }
                                    Checkbox {
                                        icon = RadioButtonUncheckedRounded.create()
                                        checkedIcon = CheckCircleRounded.create()
                                        checked = enableJvmHotReload
                                    }
                                }
                            }
                        }
                    }

                    VersionsTable {
                        sx {
                            width = textFieldWidth
                        }
                        info = default
                    }

                    val deps = setOf(
                        DependencyBox(listOf(Kermit, Napier)),
                        DependencyBox(KotlinxCoroutinesCore),
                        DependencyBox(KtorCore),
                        DependencyBox(AndroidxLifecycleViewmodel),
                        DependencyBox(listOf(AndroidxNavigation, Voyager, Decompose, PreCompose)),
                        DependencyBox(KotlinxSerializationJson),
                        DependencyBox(listOf(KotlinInject, Koin, Kodein)),
                        DependencyBox(listOf(Coil, Sketch, ImageLoader)),
                        DependencyBox(MultiplatformSettings),
                        DependencyBox(KotlinxDateTime),
                        DependencyBox(listOf(RoomPlugin, SQLDelightPlugin)),
                        DependencyBox(ApolloPlugin),
                        DependencyBox(KStore),
                        DependencyBox(listOf(BuildConfigPlugin, BuildKonfigPlugin)),
                        DependencyBox(MaterialKolor),
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
                                    if (enableJvmHotReload && platforms.contains(Jvm)) {
                                        add(ComposeHotReloadPlugin)
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
                ComposePlugin -> listOf(
                    ComposePlugin,
                    ComposeCompilerPlugin
                )
                KtorCore -> listOfNotNull(
                    KtorCore,
                    KtorClientContentNegotiation,
                    KtorClientSerialization,
                    KtorSerializationJson,
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
                KotlinInject -> listOf(
                    KotlinInject,
                    KotlinInjectCompiler,
                    DevToolKSP
                )
                KStore -> listOf(
                    KStore,
                    KStoreFile,
                    KStoreStorage
                )
                Koin -> listOf(Koin, KoinCompose)
                Coil -> listOf(Coil, CoilNetwork)
                Sketch -> listOf(Sketch, SketchHttp)
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
