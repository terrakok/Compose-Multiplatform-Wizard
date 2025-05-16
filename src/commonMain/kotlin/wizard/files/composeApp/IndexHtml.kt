package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo

class JsIndexHtml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jsMain/resources/index.html"
    override val content = getIndexHtml(info, true)
}

class WasmJsIndexHtml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/wasmJsMain/resources/index.html"
    override val content = getIndexHtml(info, false)
}

private fun getIndexHtml(info: ProjectInfo, withSkiko: Boolean) = buildString {
    appendLine("""<!doctype html>""")
    appendLine("""<html lang="en">""")
    appendLine("""    <head>""")
    appendLine("""        <meta charset="UTF-8">""")
    appendLine("""        <meta name="viewport" content="width=device-width, initial-scale=1.0">""")
    appendLine("""        <link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon.png">""")
    appendLine("""        <link rel="icon" type="image/png" sizes="32x32" href="./favicon-32x32.png">""")
    appendLine("""        <link rel="icon" type="image/png" sizes="16x16" href="./favicon-16x16.png">""")
    appendLine("""        <link rel="manifest" href="./manifest.json">""")
    appendLine("""        <title>${info.name}</title>""")
    if (withSkiko) appendLine("""        <script src="skiko.js"></script>""")
    appendLine("""        <style>""")
    appendLine("""            html,""")
    appendLine("""            body {""")
    appendLine("""                width: 100%;""")
    appendLine("""                height: 100%;""")
    appendLine("""                margin: 0;""")
    appendLine("""                padding: 0;""")
    appendLine("""                overflow: hidden;""")
    appendLine("""            }""")
    appendLine("""        </style>""")
    appendLine("""    </head>""")
    appendLine("""    <body></body>""")
    appendLine("""    <script src="${info.moduleName}.js"></script>""")
    appendLine("""</html>""")
}

class JsWebManifestJson(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jsMain/resources/manifest.json"
    override val content = getWebManifestJson(info)
}

class WasmWebManifestJson(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/wasmJsMain/resources/manifest.json"
    override val content = getWebManifestJson(info)
}

private fun getWebManifestJson(info: ProjectInfo) = """
        {
          "name": "${info.name}",
          "short_name": "${info.name}",
          "icons": [
            {
              "src": "./android-chrome-192x192.png",
              "sizes": "192x192",
              "type": "image/png"
            },
            {
              "src": "./android-chrome-512x512.png",
              "sizes": "512x512",
              "type": "image/png"
            }
          ],
          "theme_color": "#ffffff",
          "background_color": "#ffffff",
          "display": "standalone"
        }
    """.trimIndent()
