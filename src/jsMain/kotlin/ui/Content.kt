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
import wizard.*
import wizard.ProjectPlatform.*
import wizard.dependencies.*
import mui.icons.material.Android as AndroidIcon

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

                    var platforms by useState(setOf(Android, Ios, Jvm, Js))
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
                            title = Android.title
                            isSelected = platforms.contains(Android)
                            onClick = { switch(Android) }
                            icon = AndroidIcon
                        }
                        TargetButton {
                            title = Jvm.title
                            isSelected = platforms.contains(Jvm)
                            onClick = { switch(Jvm) }
                            icon = Laptop
                        }
                        TargetButton {
                            title = Ios.title
                            isSelected = platforms.contains(Ios)
                            onClick = { switch(Ios) }
                            icon = Apple
                            status = "Alpha"
                        }
                        TargetButton {
                            title = Js.title
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
                        DependencyBox(listOf(Voyager, Decompose, PreCompose), true),
                        DependencyBox(ImageLoader, true),
                        DependencyBox(listOf(Napier, Kermit), true),
                        DependencyBox(listOf(BuildConfigPlugin, BuildKonfigPlugin), true),
                        DependencyBox(KotlinxCoroutinesCore, true),
                        DependencyBox(MokoMvvm, false),
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

                        disabled = projectName.isBlank()
                                || projectId.isBlank()
                                || platforms.isEmpty()

                        onClick = {
                            val info = ProjectInfo(
                                packageId = projectId,
                                name = projectName,
                                platforms = platforms,
                                dependencies = buildSet {
                                    add(KotlinPlugin)
                                    add(ComposePlugin)
                                    if (platforms.contains(Android)) {
                                        add(AndroidApplicationPlugin)
                                        add(AndroidxAppcompat)
                                        add(AndroidxActivityCompose)
                                        add(ComposeUiTooling)
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

private fun Set<DependencyBox>.getSelectedDependencies() =
    this
        .filter { it.isSelected.component1() }
        .map { it.selectedDep.component1() }
        .flatMap {
            when {
                it.group == KtorCore.group -> listOfNotNull(KtorCore, KtorClientDarwin, KtorClientOkhttp)
                it.group == SQLDelightPlugin.group -> listOf(
                    SQLDelightPlugin,
                    SQLDelightDriverJvm,
                    SQLDelightDriverAndroid,
                    SQLDelightDriverNative,
                    SQLDelightDriverJs
                )

                it.group == Decompose.group -> listOf(Decompose, DecomposeCompose)
                it.group == ApolloPlugin.group -> listOf(ApolloPlugin, ApolloRuntime)

                it.id.contains("coroutines") -> listOf(KotlinxCoroutinesCore, KotlinxCoroutinesAndroid)
                it.id.contains("serialization") -> listOf(KotlinxSerializationPlugin, KotlinxSerializationJson)
                else -> listOf(it)
            }
        }
        .toSet()
