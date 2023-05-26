package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class ShapeKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/Shape.kt"
    override val content = """
        package ${info.packageId}

        import androidx.compose.foundation.shape.RoundedCornerShape
        import androidx.compose.material3.Shapes
        import androidx.compose.ui.unit.dp

        val Shapes = Shapes(
            extraSmall = RoundedCornerShape(2.dp),
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(16.dp),
            extraLarge = RoundedCornerShape(32.dp)
        )

    """.trimIndent()
}