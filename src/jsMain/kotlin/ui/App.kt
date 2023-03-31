package ui

import csstype.*
import mui.icons.material.*
import mui.material.*
import mui.material.Size
import mui.material.styles.ThemeProvider
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML
import react.dom.onChange
import web.html.HTMLInputElement
import web.window.window
import wizard.*

external interface AppProps : Props {
    var generate: (ProjectInfo) -> Unit
}

val ShowVersionContext = createContext<Boolean>()

val App = FC<AppProps> { props ->
    val currentThemeIsDark = kotlinx.browser.window.matchMedia("(prefers-color-scheme: dark)").matches
    val state = useState(
        if (currentThemeIsDark) Themes.Dark else Themes.Light
    )
    var theme by state

    ThemeProvider {
        this.theme = theme
        CssBaseline()

        Container {
            sx {
                padding = Padding(24.px, 24.px)
                minWidth = 650.px
            }
            Paper {
                val default = ProjectInfo()

                var showVersions by useState(false)
                var projectName by useState(default.name)
                var projectId by useState(default.packageId)
                val withAndroidState = useState(default.platforms.contains(ComposePlatform.Android))
                val withIosState = useState(default.platforms.contains(ComposePlatform.Ios))
                val withDesktopState = useState(default.platforms.contains(ComposePlatform.Desktop))
                val withBrowserState = useState(default.platforms.contains(ComposePlatform.Browser))

                sx {
                    padding = Padding(24.px, 24.px)
                }

                Box {
                    sx {
                        position = Position.relative
                    }
                    Stack {
                        direction = responsive(StackDirection.row)
                        sx {
                            position = Position.absolute
                            right = 0.px
                            top = 0.px
                        }

                        IconButton {
                            onClick = {
                                showVersions = !showVersions
                            }
                            if (showVersions) {
                                +Code.create()
                            } else {
                                +CodeOff.create()
                            }
                        }

                        IconButton {
                            onClick = {
                                window.open("https://github.com/terrakok/Compose-Multiplatform-Wizard")
                            }
                            +GitHub.create()
                        }

                        IconButton {
                            onClick = {
                                theme = if (theme == Themes.Light) Themes.Dark else Themes.Light
                            }
                            if (theme == Themes.Light) {
                                +Brightness7.create()
                            } else {
                                +Brightness4.create()
                            }
                        }
                    }
                }

                Stack {
                    direction = responsive(StackDirection.column)
                    spacing = responsive(2)
                    sx {
                        alignItems = AlignItems.center
                    }

                    Stack {
                        direction = responsive(StackDirection.row)
                        spacing = responsive(2)
                        sx {
                            alignItems = AlignItems.center
                            paddingTop = 24.px
                            paddingBottom = 24.px
                        }
                        ReactHTML.img {
                            src = "compose-logo.svg"
                            width = 150.0
                            height = 150.0
                        }
                        Typography {
                            variant = TypographyVariant.h3
                            +"Compose Multiplatform Wizard"
                        }
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

                    if (showVersions) {
                        TableContainer {
                            sx {
                                width = textFieldWidth
                            }
                            component = Paper
                            Table {
                                TableBody {
                                    TableRow {
                                        TableCell { +"Kotlin" }
                                        TableCell { +default.kotlinVersion }
                                    }
                                    TableRow {
                                        TableCell { +"Compose" }
                                        TableCell { +default.composeVersion }
                                    }
                                    TableRow {
                                        TableCell { +"Gradle" }
                                        TableCell { +default.gradleVersion }
                                    }
                                    TableRow {
                                        TableCell { +"Android Gradle Plugin" }
                                        TableCell { +default.agpVersion }
                                    }
                                }
                            }
                        }
                    }

                    val deps = mapOf(
                        Napier to useState(true),
                        LibresCompose to useState(true),
                        Voyager to useState(true),
                        ImageLoader to useState(true),
                        KotlinxCoroutinesCore to useState(true),
                        KtorCore to useState(false),
                        KotlinxSerializationJson to useState(false),
                        KotlinxDateTime to useState(false),
                        MultiplatformSettings to useState(false),
                        Koin to useState(false),
                        KStore to useState(false),
                        SQLDelightPlugin to useState(false),
                        ApolloPlugin to useState(false),
                    )
                    ShowVersionContext.Provider {
                        value = showVersions
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
