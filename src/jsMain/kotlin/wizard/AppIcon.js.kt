package wizard

import npm.SVG
import npm.Svg
import ui.materialSymbolOrNull
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