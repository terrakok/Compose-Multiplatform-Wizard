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
import wizard.*
import wizard.ProjectPlatform.*
import wizard.dependencies.*
import mui.icons.material.Android as AndroidIcon

val KmpLibraryWizardContent = FC<AppProps> { props ->
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

                var default = DefaultKmpLibraryInfo()
                var projectName by useState(default.name)
                var projectId by useState(default.packageId)
                var moduleName by useState(default.moduleName)
                var platforms by useState(default.platforms)
                var addSampleApp by useState(default.addSampleApp)
                var addSampleTests by useState(default.addTests)
                val deps: Set<DependencyBox> = setOf(
                    DependencyBox(default, ComposeMultiplatformPlugin),
                    DependencyBox(default, KotlinxCoroutinesCore),
                    DependencyBox(default, KotlinxSerializationJson),
                    DependencyBox(default, KotlinxDateTime),
                    DependencyBox(default, listOf(Kermit, Napier)),
                    DependencyBox(default, KtorCore),
                    DependencyBox(default, SQLDelightPlugin),
                    DependencyBox(default, MultiplatformSettings),
                    DependencyBox(default, listOf(BuildConfigPlugin, BuildKonfigPlugin)),
                )

                TopMenu {
                    resetProject = {
                        default = DefaultKmpLibraryInfo()
                        projectName = default.name
                        projectId = default.packageId
                        moduleName = default.moduleName
                        platforms = default.platforms
                        addSampleApp = default.addSampleApp
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
                        image = "kotlin-logo.svg"
                        title = "KMP Library Wizard"
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

                    TextField {
                        label = ReactNode("Library name")
                        sx {
                            width = textFieldWidth
                        }
                        value = moduleName
                        onChange = { event ->
                            moduleName = (event.target as HTMLInputElement).value
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
                        }
                        TargetButton {
                            title = Wasm.title
                            isSelected = platforms.contains(Wasm)
                            onClick = { switch(Wasm) }
                            icon = Preview
                        }
                    }
                    ButtonGroup {
                        disableElevation = true
                        TargetButton {
                            title = Js.title
                            isSelected = platforms.contains(Js)
                            onClick = { switch(Js) }
                            icon = Language
                        }
                        TargetButton {
                            title = Macos.title
                            isSelected = platforms.contains(Macos)
                            onClick = { switch(Macos) }
                            icon = ViewCarousel
                        }
                        TargetButton {
                            title = Linux.title
                            isSelected = platforms.contains(Linux)
                            onClick = { switch(Linux) }
                            icon = Engineering
                        }
                        TargetButton {
                            title = Mingw.title
                            isSelected = platforms.contains(Mingw)
                            onClick = { switch(Mingw) }
                            icon = Window
                        }
                    }

                    Card {
                        sx {
                            width = textFieldWidth
                        }
                        onClick = {
                            addSampleApp = !addSampleApp
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
                                        +"Add sample app"
                                    }
                                }
                                Checkbox {
                                    icon = RadioButtonUncheckedRounded.create()
                                    checkedIcon = CheckCircleRounded.create()
                                    checked = addSampleApp
                                }
                            }
                        }
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
                                || moduleName.isBlank()
                                || platforms.isEmpty()

                        onClick = {
                            val info = DefaultKmpLibraryInfo().copy(
                                packageId = projectId,
                                name = projectName,
                                moduleName = moduleName,
                                platforms = platforms,
                                addSampleApp = addSampleApp,
                                addTests = addSampleTests,
                                dependencies = buildSet {
                                    add(KotlinMultiplatformPlugin)
                                    if (platforms.contains(Android)) {
                                        add(AndroidKmpLibraryPlugin)
                                    }
                                    add(MavenPublishPlugin)
                                    addAll(deps.getSelectedDependencies())
                                }
                            )
                            props.generate(info)
                            props.save(info)
                        }
                    }
                }
            }
        }
    }
}
