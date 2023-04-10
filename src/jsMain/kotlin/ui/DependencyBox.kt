package ui

import csstype.AlignItems
import csstype.JustifyContent
import csstype.Position
import csstype.px
import csstype.unaryMinus
import mui.icons.material.CheckCircleRounded
import mui.icons.material.Edit
import mui.icons.material.RadioButtonUncheckedRounded
import mui.material.Box
import mui.material.Button
import mui.material.Card
import mui.material.CardActionArea
import mui.material.CardProps
import mui.material.Checkbox
import mui.material.GridProps
import mui.material.Stack
import mui.material.StackDirection
import mui.material.StackProps
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.responsive
import mui.system.sx
import react.FC
import react.create
import react.useState
import web.window.window
import wizard.Dependency
import wizard.files.DependencyType
import react.*

abstract class DependencyBox<P: Props> internal constructor() {

    abstract fun getAvailableDependencies(): List<Dependency>
    abstract fun getSelectedDependencies(): List<Dependency>

    abstract val renderInGrid: FC<GridProps>
    abstract val dependencyCard: FC<P>

    companion object {
        operator fun invoke(dependencyType: DependencyType): DependencyBox<*> {
            val deps = dependencyType.getDependencies()

            return if(deps.size == 1) {
                SingleDependencyBox(dependencyType, deps.single(), dependencyType.selectedByDefault)
            } else if(dependencyType.allowMultipleSelection) {
                MultiSelectDependencyTypeBox(dependencyType, deps)
            } else {
                SingleSelectDependencyTypeBox(dependencyType, deps, dependencyType.selectedByDefault)
            }
        }
    }
}

// A single dependency
// ---------------------------------------------------------------------------------------------------------------------

external interface SingleDependencyBoxProps : Props
class SingleDependencyBox(
    private val dependencyType: DependencyType,
    private val dependency: Dependency,
    isSelected: Boolean
) : DependencyBox<SingleDependencyBoxProps>() {
    private val _isDepSelected = useState(isSelected)

    private fun toggleSelection() {
        val (isSelected, setSelected) = _isDepSelected
        setSelected(!isSelected)
    }

    override fun getAvailableDependencies(): List<Dependency> {
        return listOf(dependency)
    }

    override fun getSelectedDependencies(): List<Dependency> {
        val (isDepSelected, _) = _isDepSelected
        return if(isDepSelected) {
            listOf(dependency)
        } else {
            emptyList()
        }
    }

    override val renderInGrid: FC<GridProps> = FC { props ->
        dependencyCard {
        }
    }
    override val dependencyCard: FC<SingleDependencyBoxProps> = FC { props ->
        val showVersion by useRequiredContext(ShowVersionContext)

        Card {
            sx {
                width = 320.px
            }
            onClick = {
                toggleSelection()
            }
            CardActionArea {
                Box {
                    sx {
                        position = Position.relative
                    }
                    Checkbox {
                        sx {
                            position = Position.absolute
                            right = 10.px
                            top = 10.px
                        }
                        icon = RadioButtonUncheckedRounded.create()
                        checkedIcon = CheckCircleRounded.create()
                        checked = isSelected
                    }
                }
                Stack {
                    sx {
                        padding = 16.px
                        paddingBottom = 8.px
                    }
                    spacing = responsive(1)
                    Typography {
                        variant = TypographyVariant.overline
                        +dependencyType.displayName
                    }
                    Typography {
                        variant = TypographyVariant.h5
                        +dependency.title
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        sx {
                            height = 50.px
                        }
                        +dependency.description
                    }

                    Stack {
                        direction = responsive(StackDirection.row)
                        sx {
                            justifyContent = JustifyContent.spaceBetween
                            alignItems = AlignItems.center
                        }

                        Typography {
                            variant = TypographyVariant.body1
                            if (showVersion) {
                                +dependency.version
                            }
                        }
                    }
                }
                Stack {
                    direction = responsive(StackDirection.row)
                    Button {
                        sx {
                            right = -5.px
                        }
                        onClick = {
                            it.stopPropagation()
                            window.open(dependency.url)
                        }
                        +"More info"
                    }
                }
            }
        }
    }
}

// A group of dependencies where the user may choose 0 or 1
// ---------------------------------------------------------------------------------------------------------------------

external interface SingleSelectDependencyTypeBoxProps : Props
class SingleSelectDependencyTypeBox(
    private val dependencyType: DependencyType,
    private val dependencies: List<Dependency>,
    isSelected: Boolean
) : DependencyBox<SingleSelectDependencyTypeBoxProps>() {
    private val _selectedDep = useState(dependencies[0])
    private val _isSelected = useState(isSelected)

    init {
        require(dependencies.isNotEmpty())
    }

    private fun selectIndex(i: Int) {
        require(i in dependencies.indices)
        val (_, setSelectedDep) = _selectedDep
        setSelectedDep(dependencies[i])
    }

    private fun toggleSelection() {
        val (isSelected, setSelected) = _isSelected
        setSelected(!isSelected)
    }

    override fun getAvailableDependencies(): List<Dependency> {
        return dependencies
    }

    override fun getSelectedDependencies(): List<Dependency> {
        val (isSelected, _) = _isSelected
        return if(isSelected) {
            val (selectedDep, _) = _selectedDep
            listOf(selectedDep)
        } else {
            emptyList()
        }
    }

    override val renderInGrid: FC<GridProps> = FC { props ->
        dependencyCard {
        }
    }
    override val dependencyCard: FC<SingleSelectDependencyTypeBoxProps> = FC { props ->
        val selectedDep by _selectedDep
        val isSelected by _isSelected
        val showVersion by useRequiredContext(ShowVersionContext)
        var isDialogOpen by useState(false)

        Card {
            sx {
                width = 320.px
            }
            onClick = {
                toggleSelection()
            }
            CardActionArea {
                Box {
                    sx {
                        position = Position.relative
                    }
                    Checkbox {
                        sx {
                            position = Position.absolute
                            right = 10.px
                            top = 10.px
                        }
                        icon = RadioButtonUncheckedRounded.create()
                        checkedIcon = CheckCircleRounded.create()
                        checked = isSelected
                    }
                }
                Stack {
                    sx {
                        padding = 16.px
                        paddingBottom = 8.px
                    }
                    spacing = responsive(1)
                    Typography {
                        variant = TypographyVariant.overline
                        +dependencyType.displayName
                    }
                    Typography {
                        variant = TypographyVariant.h5
                        +selectedDep.title
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        sx {
                            height = 50.px
                        }
                        +selectedDep.description
                    }

                    Stack {
                        direction = responsive(StackDirection.row)
                        sx {
                            justifyContent = JustifyContent.spaceBetween
                            alignItems = AlignItems.center
                        }

                        Typography {
                            variant = TypographyVariant.body1
                            if (showVersion) {
                                +selectedDep.version
                            }
                        }
                        Stack {
                            direction = responsive(StackDirection.row)
                            DependencyListDialog {
                                dependencies = getAvailableDependencies()
                                selectedDependencies = getSelectedDependencies()
                                allowMultiSelect = false
                                open = isDialogOpen
                                onSelection = { i ->
                                    selectIndex(i)
                                }
                                onClose = {
                                    isDialogOpen = !isDialogOpen
                                }
                            }

                            Button {
                                sx {
                                    minWidth = 20.px
                                }
                                onClick = {
                                    it.stopPropagation()
                                    isDialogOpen = !isDialogOpen
                                }
                                Edit {
                                    sx {
                                        width = 18.px
                                        height = 18.px
                                    }
                                }
                            }
                            Button {
                                sx {
                                    right = -5.px
                                }
                                onClick = {
                                    it.stopPropagation()
                                    window.open(selectedDep.url)
                                }
                                +"More info"
                            }
                        }
                    }
                }
            }
        }
    }
}

// A group of dependencies where the user may choose any number
// ---------------------------------------------------------------------------------------------------------------------

external interface MultiSelectDependencyTypeBoxProps : Props
class MultiSelectDependencyTypeBox(
    private val dependencyType: DependencyType,
    private val dependencies: List<Dependency>,
) : DependencyBox<MultiSelectDependencyTypeBoxProps>() {
    private val _selectedDeps = useState(emptyList<Dependency>())

    init {
        require(dependencies.isNotEmpty())
    }

    private fun toggleSelectedIndex(i: Int) {
        require(i in dependencies.indices)
        val (selectedDeps, setSelectedDeps) = _selectedDeps

        val dependencyAtIndex = dependencies[i]
        val updatedDeps = if(dependencyAtIndex in selectedDeps) {
            selectedDeps - dependencyAtIndex
        } else {
            selectedDeps + dependencyAtIndex
        }

        setSelectedDeps(updatedDeps)
    }

    override fun getAvailableDependencies(): List<Dependency> {
        return dependencies
    }

    override fun getSelectedDependencies(): List<Dependency> {
        val (selectedDeps, _) = _selectedDeps
        return selectedDeps
    }

    override val renderInGrid: FC<GridProps> = FC { props ->
        dependencyCard {
        }
    }

    override val dependencyCard: FC<MultiSelectDependencyTypeBoxProps> = FC { props ->
        val selectedDeps by _selectedDeps
        val firstSelectedDep = selectedDeps.firstOrNull()
        val showVersion by useRequiredContext(ShowVersionContext)
        var isDialogOpen by useState(false)

        Card {
            sx {
                width = 320.px
            }
            onClick = {
                it.stopPropagation()
                isDialogOpen = !isDialogOpen
            }
            CardActionArea {
                Box {
                    sx {
                        position = Position.relative
                    }
                    Typography {
                        sx {
                            position = Position.absolute
                            right = 10.px
                            top = 10.px
                        }
                        variant = TypographyVariant.overline
                        +"${selectedDeps.size} selected"
                    }
                }
                Stack {
                    sx {
                        padding = 16.px
                        paddingBottom = 8.px
                    }
                    spacing = responsive(1)
                    Typography {
                        variant = TypographyVariant.overline
                        +dependencyType.displayName
                    }
                    Typography {
                        variant = TypographyVariant.h5
                        +(firstSelectedDep?.title ?: "None selected")
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        sx {
                            height = 50.px
                        }
                        +(firstSelectedDep?.title ?: "")
                    }

                    Stack {
                        direction = responsive(StackDirection.row)
                        sx {
                            justifyContent = JustifyContent.spaceBetween
                            alignItems = AlignItems.center
                        }

                        Typography {
                            variant = TypographyVariant.body1
                            if (showVersion) {
                                +(firstSelectedDep?.title ?: "")
                            }
                        }
                        Stack {
                            DependencyListDialog {
                                dependencies = getAvailableDependencies()
                                selectedDependencies = getSelectedDependencies()
                                allowMultiSelect = true
                                open = isDialogOpen
                                onSelection = { i ->
                                    println("MS dialog selection: $i")
                                        toggleSelectedIndex(i)
                                }
                                onClose = {
                                    println("MS dialog closed")
                                    isDialogOpen = !isDialogOpen
                                }
                            }

                            direction = responsive(StackDirection.row)
                            Button {
                                sx {
                                    minWidth = 20.px
                                }
                                onClick = {
                                    it.stopPropagation()
                                    println("dialog button clicked")
                                    isDialogOpen = !isDialogOpen
                                }
                                Edit {
                                    sx {
                                        width = 18.px
                                        height = 18.px
                                    }
                                }
                            }
                            if(firstSelectedDep != null) {
                                Button {
                                    sx {
                                        right = -5.px
                                    }
                                    onClick = {
                                        it.stopPropagation()
                                        window.open(firstSelectedDep.url)
                                    }
                                    +"More info"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
