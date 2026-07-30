package wizard.icon

import org.khronos.webgl.ArrayBuffer
import wizard.AppIcon
import wizard.ProjectFile
import wizard.RawFile
import wizard.toPng
import kotlin.js.Promise

actual fun createIosIcon(spec: IosIconSpec, icon: AppIcon): ProjectFile = object : RawFile {
    override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/${spec.filename}"
    override val arrayBuffer: Promise<ArrayBuffer> get() = icon.toPng(spec.actualPxSize)
}
