package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo

class IndexHtml(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/jsMain/resources/index.html"
    override val content = """
        <!doctype html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>${info.name}</title>
            <script src="skiko.js"></script>
        </head>
        <body>
        <div>
            <canvas id="ComposeTarget" width="800" height="600"></canvas>
        </div>
        <script src="composeApp.js"></script>
        </body>
        </html>
    """.trimIndent()
}