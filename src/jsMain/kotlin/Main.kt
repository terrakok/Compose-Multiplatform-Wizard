import kotlinx.browser.window
import npm.FileSaverJs
import npm.JSZip
import org.khronos.webgl.ArrayBuffer
import org.w3c.files.Blob
import react.create
import react.dom.client.createRoot
import ui.App
import web.dom.document
import web.html.HtmlTagName.div
import web.html.HtmlTagName.link
import web.html.HtmlTagName.script
import wizard.BinaryFile
import wizard.BuildConfig
import wizard.ProjectInfo
import wizard.WizardType
import wizard.files.Gradlew
import wizard.generate
import wizard.safeName
import kotlin.js.Promise

fun main() {
    //title
    document.title = when (BuildConfig.wizardType) {
        WizardType.ComposeApp -> "Compose Multiplatform Wizard"
        WizardType.KmpLibrary -> "KMP Library Wizard"
    }
    //favicon
    document.createElement(link).apply {
        rel = "icon"
        href = when (BuildConfig.wizardType) {
            WizardType.ComposeApp -> "compose-logo.svg"
            WizardType.KmpLibrary -> "kotlin-logo.svg"
        }
        document.head.appendChild(this)
    }
    //counter
    document.createElement(script).apply {
        src = "//gc.zgo.at/count.js"
        async = true
        val counterId = when (BuildConfig.wizardType) {
            WizardType.ComposeApp -> "compose-multiplatform-wizard"
            WizardType.KmpLibrary -> "kotlin-multiplatform-wizard"
        }
        setAttribute("data-goatcounter", "https://$counterId.goatcounter.com/count")
        document.body.appendChild(this)
    }
    //react app
    val root = document.createElement(div).also { document.body.appendChild(it) }
    createRoot(root).render(App.create {
        generate = ::generateProject
        wizardType = BuildConfig.wizardType
    })
}

private fun generateProject(project: ProjectInfo) {
    val files = project.generate(BuildConfig.wizardType)
    val textFiles = files.filterNot { it is BinaryFile }
    val binFiles = files.filterIsInstance<BinaryFile>().map { loadBinaryFileBytes(it) }
    Promise.all(binFiles.toTypedArray()).then { binaries ->
        val zip = JSZip()
        textFiles.forEach { file ->
            if (file is Gradlew) {
                zip.file(
                    file.path,
                    file.content,
                    js("""{unixPermissions:"774"}""") //execution rights
                )
            } else {
                zip.file(
                    file.path,
                    file.content
                )
            }
        }
        binaries.forEach { bin ->
            zip.file(
                bin.origin.path,
                bin.content
            )
        }
        //execution rights require UNIX mode
        zip.generateAsync<Blob>(js("""{type:"blob",platform:"UNIX"}""")).then { blob ->
            FileSaverJs.saveAs(blob, "${project.safeName}.zip")
        }
    }
}

private data class BinaryFileContent(
    val origin: BinaryFile,
    val content: ArrayBuffer
)

private fun loadBinaryFileBytes(file: BinaryFile): Promise<BinaryFileContent> =
    window.fetch("./binaries/${file.resourcePath}")
        .then { response -> response.arrayBuffer() }
        .then { BinaryFileContent(file, it) }
