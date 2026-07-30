package wizard.icon

import org.khronos.webgl.ArrayBuffer
import wizard.AppIcon
import wizard.ProjectFile
import wizard.RawFile
import wizard.toIco
import wizard.toPng
import kotlin.js.Promise

actual fun createWebIcon(spec: WebIconSpec, icon: AppIcon): ProjectFile = object : RawFile {
    override val path = "webApp/src/commonMain/resources/${spec.filename}"
    override val arrayBuffer: Promise<ArrayBuffer> get() =
        if (spec.isPng) icon.toPng(spec.size) else icon.toIco(WebIconSpec.icoSizes)
}