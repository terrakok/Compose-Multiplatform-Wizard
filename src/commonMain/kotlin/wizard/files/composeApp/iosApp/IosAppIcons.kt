package wizard.files.composeApp.iosApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.icon.IosAppIconRenderer

fun IosAppIcons(info: ProjectInfo): List<ProjectFile> = IosAppIconRenderer.render(info.appIcon)