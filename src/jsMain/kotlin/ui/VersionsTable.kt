package ui

import mui.material.Paper
import mui.material.Table
import mui.material.TableBody
import mui.material.TableCell
import mui.material.TableContainer
import mui.material.TableRow
import mui.system.PropsWithSx
import react.FC
import react.useRequiredContext
import wizard.ProjectInfo

external interface VersionsTableProps : PropsWithSx {
    var info: ProjectInfo
}

val VersionsTable = FC<VersionsTableProps> { props ->
    val showVersions by useRequiredContext(ShowVersionContext)
    if (showVersions) {
        TableContainer {
            sx = props.sx
            component = Paper
            Table {
                TableBody {
                    TableRow {
                        TableCell { +"Kotlin" }
                        TableCell { +props.info.kotlinVersion }
                    }
                    TableRow {
                        TableCell { +"Compose" }
                        TableCell { +props.info.composeVersion }
                    }
                    TableRow {
                        TableCell { +"Gradle" }
                        TableCell { +props.info.gradleVersion }
                    }
                    TableRow {
                        TableCell { +"Android Gradle Plugin" }
                        TableCell { +props.info.agpVersion }
                    }
                }
            }
        }
    }
}