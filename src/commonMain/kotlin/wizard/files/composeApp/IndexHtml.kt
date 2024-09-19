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
    appendLine("""        <link rel="icon" href="/favicon.ico" sizes="any">""")
    appendLine("""        <title>${info.name}</title>""")
    if (withSkiko) appendLine("""        <script src="skiko.js"></script>""")
    appendLine("""        <script src="${info.moduleName}.js"></script>""")
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
    appendLine("""</html>""")
}