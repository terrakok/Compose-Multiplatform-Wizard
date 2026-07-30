package wizard

import org.khronos.webgl.ArrayBuffer
import kotlin.js.Promise

interface RawFile : ProjectFile {
    override val content get() = error("It is a raw file")
    val arrayBuffer: Promise<ArrayBuffer>
}