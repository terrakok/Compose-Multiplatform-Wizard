package npm

@JsModule("@svgdotjs/svg.js")
@JsNonModule
private external object SvgDotJs {
    fun SVG(): Svg
    fun SVG(source: dynamic): Svg
}

/** Creates a new detached `<svg>` root, to be attached with [Svg.addTo]. */
fun SVG(): Svg = SvgDotJs.SVG()

/** Adopts an existing DOM node or CSS selector. */
fun SVG(source: dynamic): Svg = SvgDotJs.SVG(source)

/** Subset of the SVG.js `Element` API. */
external interface SvgElement {
    fun attr(name: String, value: Any?): SvgElement
    fun fill(color: String): SvgElement
    fun stroke(color: String): SvgElement
    fun move(x: Double, y: Double): SvgElement
    /** Moves the element so that the center of its rendered bounding box lands on [x], [y]. */
    fun center(x: Double, y: Double): SvgElement
    fun size(width: Double, height: Double): SvgElement
    /** Moves [child] into this element. */
    fun add(child: SvgElement): SvgElement
    /** Returns the `id`, generating one when the element has none yet. */
    fun id(): String
    fun remove(): SvgElement
    fun svg(): String
}

/** Subset of the SVG.js `Container` API: an element that can create children. */
external interface SvgContainer : SvgElement {
    fun rect(width: Double, height: Double): Rect
    fun group(): SvgContainer
    fun defs(): SvgContainer
    /** Creates a `<text>` with the content as a plain text node, without `<tspan>` wrapping. */
    fun plain(content: String): Text
    fun gradient(type: String, block: (Gradient) -> Unit): Gradient
    /** Creates a `<path>` element with the given path data. */
    fun path(d: String): SvgElement
    /** Escape hatch for nodes SVG.js has no builder for, e.g. `filter`. */
    fun element(nodeName: String): SvgElement
    fun svg(svg: String): SvgElement
    fun clear(): SvgContainer
}

/** The SVG.js root canvas. */
external interface Svg : SvgContainer {
    fun addTo(parent: dynamic): Svg
    fun viewbox(x: Double, y: Double, width: Double, height: Double): Svg
    override fun size(width: Double, height: Double): Svg
}

external interface Rect : SvgElement {
    fun radius(rx: Double, ry: Double): Rect
    override fun fill(color: String): Rect
}

external interface Text : SvgElement

external interface Gradient : SvgElement {
    fun stop(offset: Double, color: String): SvgElement
    fun from(x: Double, y: Double): Gradient
    fun to(x: Double, y: Double): Gradient
    /** `url(#id)` reference, for use as a `fill` or `stroke`. */
    fun url(): String
}
