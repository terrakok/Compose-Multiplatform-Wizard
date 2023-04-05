package ui

import csstype.AlignItems
import csstype.JustifyContent
import csstype.Padding
import csstype.px
import mui.icons.material.*
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.onChange
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

                    val withAndroidState = useState(default.platforms.contains(ComposePlatform.Android))
                    val withIosState = useState(default.platforms.contains(ComposePlatform.Ios))
                    val withDesktopState = useState(default.platforms.contains(ComposePlatform.Desktop))
                    val withBrowserState = useState(default.platforms.contains(ComposePlatform.Browser))
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
                            status = "Experimental"
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

                    val deps = mapOf(
                        Kermit to useState(true),
                        Napier to useState(true),
                        LibresCompose to useState(true),
                        Voyager to useState(true),
                        ImageLoader to useState(true),
                        KotlinxCoroutinesCore to useState(true),
                        BuildConfigPlugin to useState(true),
                        KtorCore to useState(false),
                        ComposeIcons to useState(false),
                        KotlinxSerializationJson to useState(false),
                        KotlinxDateTime to useState(false),
                        MultiplatformSettings to useState(false),
                        Koin to useState(false),
                        KStore to useState(false),
                        SQLDelightPlugin to useState(false),
                        ApolloPlugin to useState(false),
                    )
                    Grid {
                        sx {
                            justifyContent = JustifyContent.spaceAround
                        }
                        spacing = responsive(2)
                        container = true
                        deps.forEach { (dep, state) ->
                            Grid {
                                item = true
                                DependencyCard {
                                    dependency = dep
                                    selection = state
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
                                dependencies = requiredAndroidDependencies + getSelectedDependencies(deps)
                            )
                            props.generate(info)
                        }
                    }
                }
            }
        }
    }
}

private fun getSelectedDependencies(deps: Map<Dependency, StateInstance<Boolean>>) =
    deps
        .filter { (_, s) -> s.component1() }
        .flatMap { (d, _) ->
            when {
                d.group == "io.github.skeptick.libres" -> listOf(LibresPlugin, LibresCompose)
                d.group == "io.ktor" -> listOfNotNull(KtorCore, KtorClientDarwin, KtorClientOkhttp)
                d.group == "app.cash.sqldelight" -> listOf(
                    SQLDelightPlugin,
                    SQLDelightDriverJvm,
                    SQLDelightDriverAndroid,
                    SQLDelightDriverNative,
                    SQLDelightDriverJs
                )

                d.group == "com.apollographql.apollo3" -> listOf(ApolloPlugin, ApolloRuntime)

                d.id.contains("coroutines") -> listOf(KotlinxCoroutinesCore, KotlinxCoroutinesAndroid)
                d.id.contains("serialization") -> listOf(KotlinxSerializationPlugin, KotlinxSerializationJson)
                else -> listOf(d)
            }
        }
        .toSet()