package wizard

import npm.CanvgModule
import npm.ICO
import npm.SVG
import npm.Svg
import org.khronos.webgl.ArrayBuffer
import org.w3c.dom.CanvasRenderingContext2D
import ui.materialSymbolOrNull
import web.dom.document
import web.html.HTMLCanvasElement
import web.html.HtmlTagName
import kotlin.js.Promise
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun AppIcon.toSvg(size: Double): String =
    SVG()
        .size(size, size)
        .viewbox(0.0, 0.0, size, size)
        .apply {
            clear()
            drawAppIcon(this@toSvg, size)
        }.svg()

fun Svg.drawAppIcon(icon: AppIcon, size: Double) {
    drawAppIconBackground(icon, size)
    drawAppIconSymbol(icon, size)
}

private fun Svg.drawAppIconBackground(icon: AppIcon, size: Double) {
    val backgroundFill = when (val background = icon.background) {
        is AppIconBackground.Solid -> background.color
        is AppIconBackground.Gradient -> addBackgroundGradient(background)
    }

    val cornerRadius = size * icon.cornerRadiusPercent / 100
    rect(size, size)
        .radius(cornerRadius, cornerRadius)
        .fill(backgroundFill)
}

private fun Svg.addBackgroundGradient(background: AppIconBackground.Gradient): String {
    val angle = background.angleDegrees * PI / 180
    return gradient("linear") { add ->
        add.stop(0.0, background.from)
        add.stop(1.0, background.to)
    }
        .from(0.5 - 0.5 * cos(angle), 0.5 - 0.5 * sin(angle))
        .to(0.5 + 0.5 * cos(angle), 0.5 + 0.5 * sin(angle))
        .url()
}

private fun Svg.drawAppIconSymbol(icon: AppIcon, size: Double) {
    val symbol = materialSymbolOrNull(icon.symbolName) ?: return
    val symbolSize = size * icon.symbolScale
    val x = (size - symbolSize) / 2
    val y = (size - symbolSize) / 2

    val nestedSvg = SVG(symbol.svgString(icon.symbolStyle))
        .size(symbolSize, symbolSize)
        .move(x, y)
        .attr("style", "color: ${icon.symbolColor}; fill: ${icon.symbolColor};")

    add(nestedSvg)
}

fun AppIcon.toPng(size: Int): Promise<ArrayBuffer> {
    val svgString = this.toSvg(size.toDouble())

    val canvas: HTMLCanvasElement = document.createElement(HtmlTagName.canvas).apply {
        width = size
        height = size
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

fun AppIcon.toIco(sizes: List<Int>): Promise<ArrayBuffer> =
     js.promise.Promise.all(
        sizes.map { toPng(it).unsafeCast<js.promise.Promise<ArrayBuffer>>() }.toTypedArray()
    ).flatThen { pngs ->
        ICO.encodeIco(pngs.map { buffer -> js("{buffer}") }.toTypedArray())
    }.unsafeCast<Promise<ArrayBuffer>>()
