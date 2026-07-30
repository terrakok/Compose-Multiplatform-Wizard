package wizard.icon

import wizard.AppIcon
import wizard.AppIconRenderer
import wizard.ProjectFile

data class IosIconSpec(
    val filename: String,
    val idiom: String,
    val scale: String,
    val size: String,
) {
    val actualPxSize = (size.substringBefore("x").toFloat() * scale.substringBefore("x").toFloat()).toInt()
}

private val iconSpecs = listOf(
    IosIconSpec("AppIcon@2x.png", "iphone", "2x", "60x60"),
    IosIconSpec("AppIcon@3x.png", "iphone", "3x", "60x60"),
    IosIconSpec("AppIcon~ipad.png", "ipad", "1x", "76x76"),
    IosIconSpec("AppIcon@2x~ipad.png", "ipad", "2x", "76x76"),
    IosIconSpec("AppIcon-83.5@2x~ipad.png", "ipad", "2x", "83.5x83.5"),
    IosIconSpec("AppIcon-40@2x.png", "iphone", "2x", "40x40"),
    IosIconSpec("AppIcon-40@3x.png", "iphone", "3x", "40x40"),
    IosIconSpec("AppIcon-40~ipad.png", "ipad", "1x", "40x40"),
    IosIconSpec("AppIcon-40@2x~ipad.png", "ipad", "2x", "40x40"),
    IosIconSpec("AppIcon-20@2x.png", "iphone", "2x", "20x20"),
    IosIconSpec("AppIcon-20@3x.png", "iphone", "3x", "20x20"),
    IosIconSpec("AppIcon-20~ipad.png", "ipad", "1x", "20x20"),
    IosIconSpec("AppIcon-20@2x~ipad.png", "ipad", "2x", "20x20"),
    IosIconSpec("AppIcon-29.png", "iphone", "1x", "29x29"),
    IosIconSpec("AppIcon-29@2x.png", "iphone", "2x", "29x29"),
    IosIconSpec("AppIcon-29@3x.png", "iphone", "3x", "29x29"),
    IosIconSpec("AppIcon-29~ipad.png", "ipad", "1x", "29x29"),
    IosIconSpec("AppIcon-29@2x~ipad.png", "ipad", "2x", "29x29"),
    IosIconSpec("AppIcon-60@2x~car.png", "car", "2x", "60x60"),
    IosIconSpec("AppIcon-60@3x~car.png", "car", "3x", "60x60"),
    IosIconSpec("AppIcon~ios-marketing.png", "ios-marketing", "1x", "1024x1024"),
)

object IosAppIconRenderer : AppIconRenderer {
    override fun render(icon: AppIcon): List<ProjectFile> {
        val iosIcon = icon.copy(cornerRadiusPercent = 0) //ios expects no rounded corners
        val iconFiles = iconSpecs.map { spec -> createIosIcon(spec, iosIcon) }
        return iconFiles + createContentJson()
    }

    private fun createContentJson(): ProjectFile = object : ProjectFile {
        override val path = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/Contents.json"
        override val content = buildString {
            appendLine("{")
            appendLine("  \"images\": [")
            iconSpecs.forEach { spec ->
                appendLine("    {")
                appendLine("      \"filename\": \"${spec.filename}\",")
                appendLine("      \"idiom\": \"${spec.idiom}\",")
                appendLine("      \"scale\": \"${spec.scale}\",")
                appendLine("      \"size\": \"${spec.size}\"")
                appendLine("    },")
            }
            appendLine("  ]")
            appendLine("}")
            appendLine()
        }
    }
}

expect fun createIosIcon(spec: IosIconSpec,  icon: AppIcon): ProjectFile