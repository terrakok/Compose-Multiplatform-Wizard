package wizard

data class AppIcon(
    val background: AppIconBackground,
    val symbolColor: String,
    /** Material Icons name of the symbol, for example `RocketLaunch`. */
    val symbolName: String,
    val symbolStyle: AppIconSymbolStyle,
    val symbolScale: Float,
    val cornerRadiusPercent: Int,
)

/** Material Icons theme the symbol is taken from. */
enum class AppIconSymbolStyle {
    Filled,
    Outlined,
}

sealed interface AppIconBackground {
    data class Solid(val color: String) : AppIconBackground
    data class Gradient(
        val from: String,
        val to: String,
        val angleDegrees: Int,
    ) : AppIconBackground
}


interface AppIconRenderer {
    fun render(icon: AppIcon): List<ProjectFile>
}