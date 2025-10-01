package wizard.files.composeApp.webApp

import wizard.BinaryFile
import wizard.ProjectInfo

fun WebFavIcons(info: ProjectInfo): List<BinaryFile> = listOf(
    getWebFavicon(info, "android-chrome-192x192.png"),
    getWebFavicon(info, "android-chrome-512x512.png"),
    getWebFavicon(info, "apple-touch-icon.png"),
    getWebFavicon(info, "favicon.ico"),
    getWebFavicon(info, "favicon-16x16.png"),
    getWebFavicon(info, "favicon-32x32.png"),
)

private fun getWebFavicon(info: ProjectInfo, fileName: String) = object : BinaryFile {
    override val path = "webApp/src/commonMain/resources/$fileName"
    override val resourcePath = "web-app-icons/$fileName"
}
