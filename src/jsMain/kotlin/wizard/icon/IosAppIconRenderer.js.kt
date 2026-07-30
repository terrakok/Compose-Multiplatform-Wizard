package wizard.icon

import npm.CanvgModule
import org.khronos.webgl.ArrayBuffer
import org.w3c.dom.CanvasRenderingContext2D
import web.dom.document
import web.html.HTMLCanvasElement
import web.html.HtmlTagName
import wizard.AppIcon
import wizard.ProjectFile
import wizard.RawFile
import wizard.toSvg
import kotlin.js.Promise

actual fun createIosIcon(spec: IosIconSpec, icon: AppIcon): ProjectFile = object : RawFile {
    override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/${spec.filename}"
    override val arrayBuffer: Promise<ArrayBuffer>
        get() {
            val svgString = icon.toSvg(spec.actualPxSize.toDouble())

            val canvas: HTMLCanvasElement = document.createElement(HtmlTagName.canvas).apply {
                width = spec.actualPxSize
                height = spec.actualPxSize
            }
            val ctx: CanvasRenderingContext2D = js("""canvas.getContext("2d");""")

            return Promise { resolve, reject ->
                CanvgModule.Canvg.from(ctx, svgString)
                    .then { canvg ->
                        canvg.render().then {
                            canvas.toBlobWithCallback(callback = { blob ->
                                if (blob == null) {
                                    reject(Throwable("Failed to create blob from canvas"))
                                } else {
                                    blob.arrayBufferAsync().then { buf ->
                                        resolve(buf.unsafeCast<ArrayBuffer>())
                                    }
                                }
                            }, type = "image/png")
                        }.catch { err -> reject(err) }
                    }.catch { err -> reject(err) }
            }.finally { canvas.remove() }
        }
}
