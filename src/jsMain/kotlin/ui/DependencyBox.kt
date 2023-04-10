package ui

import react.useState
import wizard.Dependency

class DependencyBox(
    val dependencies: List<Dependency>,
    isSelected: Boolean
) {
    constructor(dependency: Dependency, isSelected: Boolean) : this(listOf(dependency), isSelected)

    val isMultiSelect get() = dependencies.size > 1
    val selectedDep = useState(dependencies[0])
    val isSelected = useState(isSelected)

    init {
        require(dependencies.isNotEmpty())
    }

    fun selectIndex(i: Int) {
        require(i in dependencies.indices)
        val (_, setSelectedDep) = selectedDep
        setSelectedDep(dependencies[i])
    }
}