package npm

import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Promise

@JsModule("canvg")
@JsNonModule
external object CanvgModule {
    class Canvg {
        fun render(): Promise<Unit>
        companion object {
            fun from(ctx: CanvasRenderingContext2D, svg: String): Promise<Canvg>
        }
    }
}
