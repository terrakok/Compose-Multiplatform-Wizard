package ui

import react.StateInstance
import react.useState
import wizard.Dependency
import wizard.ProjectInfo

class DependencyBox(
    default: ProjectInfo,
    val dependencies: List<Dependency>,
    val dependencyKind: String = ""
) {
    constructor(default: ProjectInfo, dependency: Dependency) : this(default, listOf(dependency))

    val isMultiSelect get() = dependencies.size > 1
    val selectedDep: StateInstance<Dependency>
    val isSelected: StateInstance<Boolean>

    init {
        require(dependencies.isNotEmpty())
        val i = dependencies.indexOfFirst { d -> default.dependencies.contains(d) }
        selectedDep = useState(dependencies[maxOf(i, 0)])
        isSelected = useState(i != -1)
    }

    fun selectIndex(i: Int) {
        require(i in dependencies.indices)
        val (_, setSelectedDep) = selectedDep
        setSelectedDep(dependencies[i])
    }
}