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

fun Svg.addBackgroundGradient(background: AppIconBackground.Gradient): String {
    val angle = background.angleDegrees * PI / 180
    return gradient("linear") { add ->
        add.stop(0.0, background.from)
        add.stop(1.0, background.to)
    }
        .from(0.5 - 0.5 * cos(angle), 0.5 - 0.5 * sin(angle))
        .to(0.5 + 0.5 * cos(angle), 0.5 + 0.5 * sin(angle))
        .url()
}

fun Svg.drawAppIconSymbol(icon: AppIcon, size: Double) {
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
    return renderSvgAsPng(svgString, size)
}

fun renderSvgAsPng(svg: String, size: Int): Promise<ArrayBuffer> {
    val canvas: HTMLCanvasElement = document.createElement(HtmlTagName.canvas).apply {
        width = size
        height = size
    }
    val ctx: CanvasRenderingContext2D = js("""canvas.getContext("2d");""")

    return Promise { resolve, reject ->
        CanvgModule.Canvg.from(ctx, svg)
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

fun AppIcon.toIcns(sizes: List<Int>): Promise<ArrayBuffer> =
    js.promise.Promise.all(
        sizes.map { size ->
            toPng(size).then { png ->
                js("{size: size, buffer: png}") }.unsafeCast<js.promise.Promise<dynamic>>()
        }.toTypedArray()
    ).flatThen { pngs ->
        createIcns(pngs)
    }.unsafeCast<Promise<ArrayBuffer>>()

//language=js
private fun createIcns(pngItems: Array<dynamic>): js.promise.Promise<ArrayBuffer> = js("""
    new Promise((resolve, reject) => {
        const typeMap = {
          16: 'icp4',
          32: 'icp5',
          64: 'icp6',
          128: 'ic07',
          256: 'ic08',
          512: 'ic09',
          1024: 'ic10'
        };

        const validChunks = [];
        let totalSize = 8;

        for (const item of pngItems) {
          const typeCode = typeMap[item.size];
          const dataBytes = new Uint8Array(item.buffer);
          const chunkSize = 8 + dataBytes.length;

          validChunks.push({
            typeCode,
            chunkSize,
            dataBytes
          });

          totalSize += chunkSize;
        }

        const finalBuffer = new ArrayBuffer(totalSize);
        const view = new DataView(finalBuffer);

        view.setUint8(0, 0x69); // 'i'
        view.setUint8(1, 0x63); // 'c'
        view.setUint8(2, 0x6e); // 'n'
        view.setUint8(3, 0x73); // 's'
        view.setUint32(4, totalSize, false);

        const finalArray = new Uint8Array(finalBuffer);
        let offset = 8;

        for (const chunk of validChunks) {
          for (let i = 0; i < 4; i++) {
            view.setUint8(offset + i, chunk.typeCode.charCodeAt(i));
          }
          
          view.setUint32(offset + 4, chunk.chunkSize, false);
          offset += 8;

          finalArray.set(chunk.dataBytes, offset);
          offset += chunk.dataBytes.length;
        }

        resolve(finalBuffer);
      });
""")
