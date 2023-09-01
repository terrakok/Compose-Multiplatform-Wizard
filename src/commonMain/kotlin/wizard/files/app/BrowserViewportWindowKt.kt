package wizard.files.app

import wizard.ProjectFile

class BrowserViewportWindowKt  : ProjectFile {
    override val path = "composeApp/src/jsMain/kotlin/BrowserViewportWindow.kt"
    override val content = """
        @file:Suppress(
            "INVISIBLE_MEMBER",
            "INVISIBLE_REFERENCE",
            "EXPOSED_PARAMETER_TYPE"
        ) // WORKAROUND: ComposeWindow and ComposeLayer are internal
        
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.createSkiaLayer
        import androidx.compose.ui.native.ComposeLayer
        import androidx.compose.ui.platform.JSTextInputService
        import androidx.compose.ui.unit.Density
        import androidx.compose.ui.window.ComposeWindow
        import kotlinx.browser.document
        import kotlinx.browser.window
        import org.w3c.dom.HTMLCanvasElement
        import org.w3c.dom.HTMLStyleElement
        import org.w3c.dom.HTMLTitleElement
        
        /**
         * A Skiko/Canvas-based top-level window using the browser's entire viewport. Supports resizing.
         * Author: https://github.com/OliverO2
         * Source: https://github.com/OliverO2/compose-counting-grid/blob/master/src/frontendJsMain/kotlin/BrowserViewportWindow.kt
         */
        @Suppress("FunctionName")
        fun BrowserViewportWindow(
            title: String = "Untitled",
            content: @Composable () -> Unit,
        ) {
            AutoSizingComposeWindow(title).apply {
                setContent {
                    content()
                }
            }
        }
        
        private const val CANVAS_ELEMENT_ID = "ComposeTarget"
        
        private class AutoSizingComposeWindow(title: String) {
        
            private val density: Density = Density(
                density = window.devicePixelRatio.toFloat(),
                fontScale = 1f
            )
        
            private val jsTextInputService = JSTextInputService()
            val platform = ComposeWindow().platform
            private val layer = ComposeLayer(
                layer = createSkiaLayer(),
                platform = platform,
                input = jsTextInputService.input
            )
        
            var canvas = document.getElementById(CANVAS_ELEMENT_ID) as HTMLCanvasElement
        
            private val htmlHeadElement = document.head!!
        
            private fun resizeCanvasToViewport() {
                val scale = layer.layer.contentScale
                val density = window.devicePixelRatio.toFloat()
        
                // Cloning the canvas node to work around multiple event listeners being applied by
                // SkiaLayer.attachTo() on every resize event.
                // See https://github.com/JetBrains/compose-multiplatform-core/pull/692/files
                val oldCanvas = canvas
                canvas = oldCanvas.cloneNode(true) as HTMLCanvasElement
                oldCanvas.parentElement!!.replaceChild(canvas, oldCanvas)
        
                canvas.width = window.innerWidth
                canvas.height = window.innerHeight
                canvas.tabIndex = 0
                layer.layer.attachTo(canvas)
                layer.layer.needRedraw()
                layer.setSize(
                    (canvas.width / scale * density).toInt(),
                    (canvas.height / scale * density).toInt()
                )
            }
        
            init {
                htmlHeadElement.appendChild(
                    (document.createElement("style") as HTMLStyleElement).apply {
                        type = "text/css"
                        appendChild(
                            document.createTextNode(
                                """
                                html, body {
                                    overflow: hidden;
                                    margin: 0 !important;
                                    padding: 0 !important;
                                }
            
                                #$CANVAS_ELEMENT_ID {
                                    outline: none;
                                }
                                """.trimIndent()
                            )
                        )
                    }
                )
        
                resizeCanvasToViewport()
        
                window.addEventListener("resize", { resizeCanvasToViewport() })
        
                val htmlTitleElement = (
                        htmlHeadElement.getElementsByTagName("title").item(0)
                            ?: document.createElement("title").also { htmlHeadElement.appendChild(it) }
                        ) as HTMLTitleElement
                htmlTitleElement.textContent = title
            }
        
            /**
             * Sets Compose content of the ComposeWindow.
             *
             * @param content Composable content of the ComposeWindow.
             */
            fun setContent(
                content: @Composable () -> Unit,
            ) {
                layer.setDensity(density)
                layer.setContent(
                    content = content
                )
            }
        
            // TODO: need to call .dispose() on window close.
            fun dispose() {
                layer.dispose()
            }
        }

    """.trimIndent()
}
