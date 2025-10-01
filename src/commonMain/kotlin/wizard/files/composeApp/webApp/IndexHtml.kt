package wizard.files.composeApp.webApp

import wizard.ProjectFile
import wizard.ProjectInfo

class WebIndexHtml(info: ProjectInfo) : ProjectFile {
    override val path = "webApp/src/commonMain/resources/index.html"
    override val content = buildString {
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
        appendLine("""    <script src="webApp.js"></script>""")
        appendLine("""</html>""")
    }
}

class WebManifestJson(info: ProjectInfo) : ProjectFile {
    override val path = "webApp/src/commonMain/resources/manifest.json"
    override val content = """
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
}
