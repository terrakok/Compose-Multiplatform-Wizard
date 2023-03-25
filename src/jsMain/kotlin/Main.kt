import kotlinx.browser.window
import npm.FileSaverJs
import npm.JSZip
import org.w3c.files.Blob
import react.create
import react.dom.client.createRoot
import ui.App
import web.dom.document
import web.html.HTML.div
import wizard.ProjectInfo
import wizard.buildFiles
import wizard.files.GradleWrapperJar
import wizard.files.Gradlew
import wizard.safeName

fun main() {
    val root = document.createElement(div).also { document.body.appendChild(it) }
    createRoot(root).render(App.create {
        generate = ::generateProject
    })
}

private fun generateProject(project: ProjectInfo) {
    window.fetch("./binaries/gradle-wrapper")
        .then { response -> response.arrayBuffer() }
        .then { gradleWrapperBlob ->
            val zip = JSZip()
            project.buildFiles().forEach { file ->
                when (file) {
                    is GradleWrapperJar -> zip.file(
                        file.path,
                        gradleWrapperBlob
                    )

                    is Gradlew -> zip.file(
                        file.path,
                        file.content,
                        js("""{unixPermissions:"774"}""") //execution rights
                    )

                    else -> zip.file(
                        file.path,
                        file.content
                    )
                }
            }
            //execution rights require UNIX mode
            zip.generateAsync<Blob>(js("""{type:"blob",platform:"UNIX"}""")).then { blob ->
                FileSaverJs.saveAs(blob, "${project.safeName}.zip")
            }
        }
}
