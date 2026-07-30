import js.typedarrays.Uint8Array
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.json.Json
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
import wizard.RawFile
import wizard.BuildConfig
import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.WizardType
import wizard.generate
import wizard.safeName
import kotlin.js.Promise
import kotlin.js.unsafeCast

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
        restored = previousGeneratedProject
        save = { previousGeneratedProject = it }
        generate = ::generateProject
        wizardType = BuildConfig.wizardType
    })
}

private val PREVIOUS_GENERATED_PROJECT_KEY = "previousGeneratedProject_" + BuildConfig.wizardType.name
private var previousGeneratedProject: ProjectInfo?
    get() = localStorage.getItem(PREVIOUS_GENERATED_PROJECT_KEY)?.let { json ->
        Json.decodeFromString<ProjectInfo>(json)
    }
    set(value) {
        if (value == null) {
            localStorage.removeItem(PREVIOUS_GENERATED_PROJECT_KEY)
        } else {
            val json = Json.encodeToString(value)
            localStorage.setItem(PREVIOUS_GENERATED_PROJECT_KEY, json)
        }
    }

private fun generateProject(project: ProjectInfo) {
    val files = project.generate(BuildConfig.wizardType)
    val textFiles = files.filter { it !is BinaryFile && it !is RawFile }
    val binFiles = files.filterIsInstance<BinaryFile>().map { loadBinaryFileBytes(it) }
    val rawFiles = files.filterIsInstance<RawFile>().map { loadRawFileBytes(it) }
    Promise.all(binFiles.toTypedArray()).then { binaries ->
        Promise.all(rawFiles.toTypedArray()).then { raws ->
            val zip = JSZip()
            textFiles.forEach { file ->
                zip.file(
                    file.path,
                    file.content
                )
            }
            binaries.forEach { bin ->
                if (bin.origin.path.endsWith("gradlew")) {
                    val uint8Array = Uint8Array(bin.content.unsafeCast<js.buffer.ArrayBuffer>())
                    val string = uint8Array.unsafeCast<ByteArray>().decodeToString()
                    zip.file(
                        bin.origin.path,
                        string,
                        js("""{unixPermissions:"774"}""") //execution rights
                    )
                } else {
                    zip.file(
                        bin.origin.path,
                        bin.content
                    )
                }
            }
            raws.forEach { raw ->
                zip.file(
                    raw.origin.path,
                    raw.content
                )
            }
            //execution rights require UNIX mode
            zip.generateAsync<Blob>(js("""{type:"blob",platform:"UNIX"}""")).then { blob ->
                FileSaverJs.saveAs(blob, "${project.safeName}.zip")
            }
        }
    }
}

private data class FileContent(
    val origin: ProjectFile,
    val content: ArrayBuffer
)

private fun loadBinaryFileBytes(file: BinaryFile): Promise<FileContent> =
    window.fetch("./binaries/${file.resourcePath}")
        .then { response -> response.arrayBuffer() }
        .then { FileContent(file, it) }

private fun loadRawFileBytes(file: RawFile): Promise<FileContent> =
    file.arrayBuffer.then { FileContent(file, it) }
