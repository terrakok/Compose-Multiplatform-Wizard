package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class TypeKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/Type.kt"
    override val content = """
        package ${info.packageId}

        import androidx.compose.material3.Typography

        val Typography = Typography(
            /**
            bodyMedium = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            */
        )

    """.trimIndent()
}