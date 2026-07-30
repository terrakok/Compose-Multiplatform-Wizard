package wizard.icon

import wizard.AppIcon
import wizard.BinaryFile
import wizard.ProjectFile

actual fun createIosIcon(spec: IosIconSpec, icon: AppIcon): ProjectFile = object : BinaryFile {
    override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/${spec.filename}"
    override val resourcePath = "ios-app-icons/${spec.filename}"
}