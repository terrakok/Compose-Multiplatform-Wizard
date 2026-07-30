package wizard.icon

import wizard.AppIcon
import wizard.BinaryFile
import wizard.ProjectFile

actual fun createWebIcon(spec: WebIconSpec, icon: AppIcon): ProjectFile = object : BinaryFile {
    override val path = "webApp/src/commonMain/resources/${spec.filename}"
    override val resourcePath = "web-app-icons/$${spec.filename}"
}
