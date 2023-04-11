package ui

import csstype.AlignItems
import csstype.JustifyContent
import csstype.Padding
import csstype.px
import mui.icons.material.*
import mui.material.*
import mui.system.Container
import mui.system.responsive
import mui.system.sx
import react.FC
import react.ReactNode
import react.create
import react.dom.onChange
import react.useState
import web.html.HTMLInputElement
import wizard.*

val Content = FC<AppProps> { props ->
    Container {
        sx {
            padding = Padding(24.px, 24.px)
            minWidth = 650.px
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

                    Header()

                    val default = ProjectInfo()
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

                    val withAndroidState = useState(true)
                    val withIosState = useState(true)
                    val withDesktopState = useState(true)
                    val withBrowserState = useState(true)
                    ButtonGroup {
                        disableElevation = true
                        TargetButton {
                            selection = withAndroidState
                            icon = Android
                            title = "Android"
                        }
                        TargetButton {
                            selection = withDesktopState
                            icon = Laptop
                            title = "Desktop"
                        }
                        TargetButton {
                            selection = withIosState
                            icon = Apple
                            title = "iOS"
                            status = "Alpha"
                        }
                        TargetButton {
                            selection = withBrowserState
                            icon = Language
                            title = "Browser"
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
                        DependencyBox(LibresCompose, true),
                        DependencyBox(Voyager, true),
                        DependencyBox(ImageLoader, true),
                        DependencyBox(listOf(Napier, Kermit), true),
                        DependencyBox(listOf(BuildConfigPlugin, BuildKonfigPlugin), true),
                        DependencyBox(KotlinxCoroutinesCore, true),
                        DependencyBox(KtorCore, false),
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
                        DependencyBox(KotlinxSerializationJson, false),
                        DependencyBox(KotlinxDateTime, false),
                        DependencyBox(MultiplatformSettings, false),
                        DependencyBox(listOf(Koin, Kodein), false),
                        DependencyBox(KStore, false),
                        DependencyBox(SQLDelightPlugin, false),
                        DependencyBox(ApolloPlugin, false),
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

                        val withAndroid by withAndroidState
                        val withIos by withIosState
                        val withDesktop by withDesktopState
                        val withBrowser by withBrowserState
                        disabled = projectName.isBlank()
                                || projectId.isBlank()
                                || (!withAndroid && !withIos && !withDesktop && !withBrowser)

                        onClick = {
                            val info = ProjectInfo(
                                packageId = projectId,
                                name = projectName,
                                platforms = buildSet {
                                    if (withAndroid) add(ComposePlatform.Android)
                                    if (withIos) add(ComposePlatform.Ios)
                                    if (withDesktop) add(ComposePlatform.Desktop)
                                    if (withBrowser) add(ComposePlatform.Browser)
                                },
                                dependencies = requiredAndroidDependencies + deps.getSelectedDependencies()
                            )
                            props.generate(info)
                        }
                    }
                }
            }
        }
    }
}

private fun Set<DependencyBox>.getSelectedDependencies() =
    this
        .filter { it.isSelected.component1() }
        .map { it.selectedDep.component1() }
        .flatMap {
            when {
                it.group == "io.github.skeptick.libres" -> listOf(LibresPlugin, LibresCompose)
                it.group == "io.ktor" -> listOfNotNull(KtorCore, KtorClientDarwin, KtorClientOkhttp)
                it.group == "app.cash.sqldelight" -> listOf(
                    SQLDelightPlugin,
                    SQLDelightDriverJvm,
                    SQLDelightDriverAndroid,
                    SQLDelightDriverNative,
                    SQLDelightDriverJs
                )

                it.group == "com.apollographql.apollo3" -> listOf(ApolloPlugin, ApolloRuntime)

                it.id.contains("coroutines") -> listOf(KotlinxCoroutinesCore, KotlinxCoroutinesAndroid)
                it.id.contains("serialization") -> listOf(KotlinxSerializationPlugin, KotlinxSerializationJson)
                else -> listOf(it)
            }
        }
        .toSet()
