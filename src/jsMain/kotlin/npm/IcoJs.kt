package npm

import js.promise.Promise
import org.khronos.webgl.ArrayBuffer

@JsModule("icojs")
@JsNonModule
external object ICO {
    fun encodeIco(iconList: Array<dynamic>): Promise<ArrayBuffer>
}
