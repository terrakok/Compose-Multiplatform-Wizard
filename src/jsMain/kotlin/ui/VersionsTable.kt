package ui

import mui.material.Paper
import mui.material.Table
import mui.material.TableBody
import mui.material.TableCell
import mui.material.TableContainer
import mui.material.TableRow
import mui.system.PropsWithSx
import react.FC
import react.useRequired
import wizard.*
import wizard.dependencies.*

external interface VersionsTableProps : PropsWithSx {
    var info: ProjectInfo
}

val VersionsTable = FC<VersionsTableProps> { props ->
    val showVersions by useRequired(ShowVersionContext)
    val wizardType by useRequired(WizardTypeContext)
    if (showVersions) {
        TableContainer {
            sx = props.sx
            component = Paper
            Table {
                TableBody {
                    TableRow {
                        TableCell { +"Kotlin" }
                        TableCell { +KotlinPlugin.version }
                    }
                    TableRow {
                        TableCell { +"Android Gradle Plugin" }
                        TableCell { +AndroidApplicationPlugin.version }
                    }
                    if (wizardType == WizardType.ComposeApp) {
                        TableRow {
                            TableCell { +"Compose" }
                            TableCell { +ComposePlugin.version }
                        }
                    }
                    TableRow {
                        TableCell { +"Gradle" }
                        TableCell { +props.info.gradleVersion }
                    }
                }
            }
        }
    }
}