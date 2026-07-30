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
import wizard.*
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

                var default = props.restored ?: DefaultComposeAppInfo()
                var projectName by useState(default.name)
                var projectId by useState(default.packageId)
                var platforms by useState(default.platforms)
                var appIcon by useState(default.appIcon)
                var addSampleTests by useState(default.addTests)
                val deps = setOf(
                    DependencyBox(default, listOf(Kermit, Napier)),
                    DependencyBox(default, KotlinxCoroutinesCore),
                    DependencyBox(default, KtorCore),
                    DependencyBox(default, AndroidxLifecycleViewmodel),
                    DependencyBox(default, listOf(AndroidxNavigation3, AndroidxNavigation, Voyager, Decompose, PreCompose)),
                    DependencyBox(default, KotlinxSerializationJson),
                    DependencyBox(default, listOf(Metro, KotlinInject, Koin, Kodein)),
                    DependencyBox(default, listOf(Coil, Sketch, ImageLoader)),
                    DependencyBox(default, MultiplatformSettings),
                    DependencyBox(default, KotlinxDateTime),
                    DependencyBox(default, listOf(RoomPlugin, SQLDelightPlugin)),
                    DependencyBox(default, ApolloPlugin),
                    DependencyBox(default, KStore),
                    DependencyBox(default, listOf(BuildConfigPlugin, BuildKonfigPlugin)),
                    DependencyBox(default, MaterialKolor),
                )

                TopMenu {
                    resetProject = {
                        default = DefaultComposeAppInfo()
                        projectName = default.name
                        projectId = default.packageId
                        platforms = default.platforms
                        appIcon = default.appIcon
                        addSampleTests = default.addTests
                        deps.applySelectedFrom(default)
                        props.save(null)
                    }
                }

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

                    val textFieldWidth = 565.px

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
                            title = "Web"
                            isSelected = platforms.contains(Wasm)
                            onClick = { switch(Wasm) }
                            icon = Language
                        }
                    }

                    AppIconPreview {
                        iconSpec = appIcon
                        onIconSpecChange = { appIcon = it }
                    }

                    Card {
                        sx {
                            width = textFieldWidth
                        }
                        onClick = {
                            addSampleTests = !addSampleTests
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
                                        +"Add sample tests"
                                    }
                                }
                                Checkbox {
                                    icon = RadioButtonUncheckedRounded.create()
                                    checkedIcon = CheckCircleRounded.create()
                                    checked = addSampleTests
                                }
                            }
                        }
                    }

                    VersionsTable {
                        sx {
                            width = textFieldWidth
                        }
                    }

                    Grid {
                        sx {
                            justifyContent = JustifyContent.spaceAround
                        }
                        spacing = responsive(2)
                        container = true
                        deps.forEach { dep ->
                            Grid {
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
                            val info = DefaultComposeAppInfo().copy(
                                packageId = projectId,
                                name = projectName,
                                platforms = getActualPlatforms(platforms),
                                addTests = addSampleTests,
                                appIcon = appIcon,
                                dependencies = buildSet {
                                    add(KotlinMultiplatformPlugin)
                                    add(ComposeCompilerPlugin)
                                    add(ComposeMultiplatformPlugin)
                                    addAll(DefaultComposeLibraries)
                                    if (platforms.contains(Android)) {
                                        add(AndroidApplicationPlugin)
                                        add(AndroidKmpLibraryPlugin)
                                        add(AndroidxActivityCompose)
                                    }
                                    if (platforms.contains(Jvm)) {
                                        add(KotlinJvmPlugin)
                                    }
                                    addAll(deps.getSelectedDependencies())
                                }
                            )
                            props.save(info)
                            props.generate(info)
                        }
                    }
                }
            }
        }
    }
}

private fun getActualPlatforms(platforms: Set<ProjectPlatform>): Set<ProjectPlatform> =
    if (platforms.contains(Wasm)) platforms + Js else platforms - Js

internal fun Set<DependencyBox>.applySelectedFrom(info: ProjectInfo) {
    this.forEach { box ->
        val i = box.dependencies.indexOfFirst { d -> info.dependencies.contains(d) }
        val (_, setIsSelected) = box.isSelected
        if (i != -1) {
            setIsSelected(true)
            box.selectIndex(i)
        } else {
            setIsSelected(false)
            box.selectIndex(0)
        }
    }
}

internal fun Set<DependencyBox>.getSelectedDependencies(): Set<Dependency> {
    val selectedDeps = this
        .filter { it.isSelected.component1() }
        .map { it.selectedDep.component1() }
        .toSet()

    return selectedDeps
        .flatMap { dep ->
            when (dep) {
                ComposeMultiplatformPlugin -> listOf(
                    ComposeMultiplatformPlugin,
                    ComposeCompilerPlugin,
                    ComposeRuntime,
                    ComposeUi,
                    ComposeFoundation,
                    ComposeResources,
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
                AndroidxLifecycleViewmodel -> {
                    if (selectedDeps.contains(AndroidxNavigation3))
                        listOf(
                            AndroidxLifecycleViewmodel,
                            AndroidxLifecycleRuntime,
                            AndroidxLifecycleViewmodelNavigation3
                        )
                    else
                        listOf(AndroidxLifecycleViewmodel, AndroidxLifecycleRuntime)
                }
                AndroidxNavigation3 -> listOf(AndroidxNavigation3, Navigation3Browser)
                KotlinxCoroutinesCore -> listOf(
                    KotlinxCoroutinesCore,
                    KotlinxCoroutinesAndroid,
                    KotlinxCoroutinesJvm,
                    KotlinxCoroutinesTest
                )

                KotlinxSerializationJson -> listOf(KotlinxSerializationPlugin, KotlinxSerializationJson)
                else -> listOf(dep)
            }
        }
        .toSet()
}
