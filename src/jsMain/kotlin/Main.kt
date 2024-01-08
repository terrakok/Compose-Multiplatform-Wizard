import kotlinx.browser.window
import npm.FileSaverJs
import npm.JSZip
import org.w3c.files.Blob
import react.create
import react.dom.client.createRoot
import ui.App
import web.dom.document
import web.html.HTML.div
import web.html.HTML.link
import web.html.HTML.script
import wizard.*
import wizard.files.GradleWrapperJar
import wizard.files.Gradlew

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
    window.fetch("./binaries/gradle-wrapper")
        .then { response -> response.arrayBuffer() }
        .then { gradleWrapperBlob ->
            val zip = JSZip()
            project.generate(BuildConfig.wizardType).forEach { file ->
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
