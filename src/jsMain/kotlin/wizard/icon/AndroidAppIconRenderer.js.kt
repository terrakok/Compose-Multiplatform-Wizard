package wizard.icon

import npm.SVG
import org.khronos.webgl.ArrayBuffer
import wizard.AppIcon
import wizard.AppIconBackground
import wizard.BinaryFile
import wizard.ProjectFile
import wizard.RawFile
import wizard.addBackgroundGradient
import wizard.drawAppIcon
import wizard.drawAppIconSymbol
import wizard.renderSvgAsPng
import wizard.toPng
import kotlin.js.Promise

actual fun createAndroidLauncherIcon(path: String, icon: AppIcon, size: Int): ProjectFile = object : RawFile {
    override val path = "androidApp/src/main/res/$path"
    override val arrayBuffer: Promise<ArrayBuffer> get() = icon.toPng(size)
}

actual fun createAndroidBackgroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile {
    val s = size.toDouble()
    val svg = SVG()
        .size(s, s)
        .viewbox(0.0, 0.0, s, s)
        .apply {
            clear()
            val backgroundFill = when (val background = icon.background) {
                is AppIconBackground.Solid -> background.color
                is AppIconBackground.Gradient -> addBackgroundGradient(background)
            }
            rect(s, s).fill(backgroundFill)
        }.svg()

    return object : RawFile {
        override val path = "androidApp/src/main/res/$path"
        override val arrayBuffer: Promise<ArrayBuffer> get() = renderSvgAsPng(svg, size)
    }
}

actual fun createAndroidForegroundIcon(path: String, icon: AppIcon, size: Int): ProjectFile {
    val foregroundIcon = icon.copy(symbolScale = icon.symbolScale * 0.65f)
    val s = size.toDouble()
    val svg = SVG()
        .size(s, s)
        .viewbox(0.0, 0.0, s, s)
        .apply {
            clear()
            drawAppIconSymbol(foregroundIcon, s)
        }.svg()

    return object : RawFile {
        override val path = "androidApp/src/main/res/$path"
        override val arrayBuffer: Promise<ArrayBuffer> get() = renderSvgAsPng(svg, size)
    }
}

actual fun createAndroidMonochromeIcon(path: String, icon: AppIcon, size: Int): ProjectFile {
    val monochromeIcon = icon.copy(symbolColor = "#000000")
    val s = size.toDouble()
    val svg = SVG()
        .size(s, s)
        .viewbox(0.0, 0.0, s, s)
        .apply {
            clear()
            drawAppIconSymbol(monochromeIcon, s)
        }.svg()

    return object : RawFile {
        override val path = "androidApp/src/main/res/$path"
        override val arrayBuffer: Promise<ArrayBuffer> get() = renderSvgAsPng(svg, size)
    }
}
