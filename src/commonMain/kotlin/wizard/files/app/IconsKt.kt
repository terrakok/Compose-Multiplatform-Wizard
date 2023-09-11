package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

class IconsKt(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/commonMain/kotlin/${info.packagePath}/Icons.kt"
    override val content = """
        package ${info.packageId}

        import androidx.compose.runtime.Composable
        import androidx.compose.runtime.remember
        import androidx.compose.ui.graphics.Color
        import androidx.compose.ui.graphics.PathFillType
        import androidx.compose.ui.graphics.SolidColor
        import androidx.compose.ui.graphics.StrokeCap
        import androidx.compose.ui.graphics.StrokeJoin
        import androidx.compose.ui.graphics.vector.ImageVector
        import androidx.compose.ui.graphics.vector.path
        import androidx.compose.ui.unit.dp

        //https://www.composables.com/icons
        @Composable
        fun rememberLightMode(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "light_mode",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(20f, 25.625f)
                        quadToRelative(2.333f, 0f, 3.979f, -1.646f)
                        reflectiveQuadTo(25.625f, 20f)
                        quadToRelative(0f, -2.333f, -1.646f, -3.979f)
                        reflectiveQuadTo(20f, 14.375f)
                        quadToRelative(-2.333f, 0f, -3.979f, 1.646f)
                        reflectiveQuadTo(14.375f, 20f)
                        quadToRelative(0f, 2.333f, 1.646f, 3.979f)
                        reflectiveQuadTo(20f, 25.625f)
                        close()
                        moveToRelative(0f, 2.625f)
                        quadToRelative(-3.417f, 0f, -5.833f, -2.417f)
                        quadTo(11.75f, 23.417f, 11.75f, 20f)
                        quadToRelative(0f, -3.417f, 2.417f, -5.854f)
                        quadToRelative(2.416f, -2.438f, 5.833f, -2.438f)
                        quadToRelative(3.417f, 0f, 5.854f, 2.438f)
                        quadToRelative(2.438f, 2.437f, 2.438f, 5.854f)
                        quadToRelative(0f, 3.417f, -2.438f, 5.833f)
                        quadTo(23.417f, 28.25f, 20f, 28.25f)
                        close()
                        moveTo(3.042f, 21.292f)
                        quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                        reflectiveQuadTo(1.75f, 20f)
                        quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                        quadToRelative(0.375f, -0.395f, 0.917f, -0.395f)
                        horizontalLineToRelative(3.916f)
                        quadToRelative(0.542f, 0f, 0.938f, 0.395f)
                        quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                        quadToRelative(0f, 0.542f, -0.396f, 0.917f)
                        reflectiveQuadToRelative(-0.938f, 0.375f)
                        close()
                        moveToRelative(30f, 0f)
                        quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                        reflectiveQuadTo(31.75f, 20f)
                        quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                        quadToRelative(0.375f, -0.395f, 0.917f, -0.395f)
                        horizontalLineToRelative(3.916f)
                        quadToRelative(0.542f, 0f, 0.938f, 0.395f)
                        quadToRelative(0.396f, 0.396f, 0.396f, 0.938f)
                        quadToRelative(0f, 0.542f, -0.396f, 0.917f)
                        reflectiveQuadToRelative(-0.938f, 0.375f)
                        close()
                        moveTo(20f, 8.25f)
                        quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                        reflectiveQuadToRelative(-0.375f, -0.917f)
                        verticalLineTo(3.042f)
                        quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                        quadToRelative(0.375f, -0.396f, 0.917f, -0.396f)
                        reflectiveQuadToRelative(0.938f, 0.396f)
                        quadToRelative(0.395f, 0.396f, 0.395f, 0.938f)
                        verticalLineToRelative(3.916f)
                        quadToRelative(0f, 0.542f, -0.395f, 0.917f)
                        quadToRelative(-0.396f, 0.375f, -0.938f, 0.375f)
                        close()
                        moveToRelative(0f, 30f)
                        quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                        reflectiveQuadToRelative(-0.375f, -0.917f)
                        verticalLineToRelative(-3.916f)
                        quadToRelative(0f, -0.542f, 0.375f, -0.938f)
                        quadToRelative(0.375f, -0.396f, 0.917f, -0.396f)
                        reflectiveQuadToRelative(0.938f, 0.396f)
                        quadToRelative(0.395f, 0.396f, 0.395f, 0.938f)
                        verticalLineToRelative(3.916f)
                        quadToRelative(0f, 0.542f, -0.395f, 0.917f)
                        quadToRelative(-0.396f, 0.375f, -0.938f, 0.375f)
                        close()
                        moveTo(9.875f, 11.708f)
                        lineTo(7.667f, 9.542f)
                        quadToRelative(-0.417f, -0.375f, -0.396f, -0.917f)
                        quadToRelative(0.021f, -0.542f, 0.396f, -0.958f)
                        quadToRelative(0.416f, -0.375f, 0.958f, -0.396f)
                        quadToRelative(0.542f, -0.021f, 0.917f, 0.396f)
                        lineToRelative(2.166f, 2.166f)
                        quadToRelative(0.375f, 0.417f, 0.375f, 0.938f)
                        quadToRelative(0f, 0.521f, -0.375f, 0.937f)
                        quadToRelative(-0.375f, 0.375f, -0.916f, 0.355f)
                        quadToRelative(-0.542f, -0.021f, -0.917f, -0.355f)
                        close()
                        moveToRelative(20.583f, 20.625f)
                        lineToRelative(-2.166f, -2.208f)
                        quadToRelative(-0.334f, -0.375f, -0.354f, -0.917f)
                        quadToRelative(-0.021f, -0.541f, 0.395f, -0.916f)
                        quadToRelative(0.334f, -0.417f, 0.875f, -0.396f)
                        quadToRelative(0.542f, 0.021f, 0.959f, 0.396f)
                        lineToRelative(2.166f, 2.166f)
                        quadToRelative(0.417f, 0.375f, 0.396f, 0.917f)
                        quadToRelative(-0.021f, 0.542f, -0.396f, 0.958f)
                        quadToRelative(-0.416f, 0.375f, -0.958f, 0.396f)
                        quadToRelative(-0.542f, 0.021f, -0.917f, -0.396f)
                        close()
                        moveToRelative(-2.166f, -20.625f)
                        quadToRelative(-0.417f, -0.375f, -0.396f, -0.916f)
                        quadToRelative(0.021f, -0.542f, 0.396f, -0.959f)
                        lineToRelative(2.166f, -2.166f)
                        quadToRelative(0.375f, -0.417f, 0.917f, -0.396f)
                        quadToRelative(0.542f, 0.021f, 0.958f, 0.396f)
                        quadToRelative(0.375f, 0.416f, 0.396f, 0.958f)
                        quadToRelative(0.021f, 0.542f, -0.396f, 0.917f)
                        lineToRelative(-2.166f, 2.166f)
                        quadToRelative(-0.375f, 0.375f, -0.917f, 0.375f)
                        reflectiveQuadToRelative(-0.958f, -0.375f)
                        close()
                        moveTo(7.667f, 32.333f)
                        quadToRelative(-0.375f, -0.416f, -0.396f, -0.958f)
                        quadToRelative(-0.021f, -0.542f, 0.396f, -0.917f)
                        lineToRelative(2.208f, -2.166f)
                        quadToRelative(0.375f, -0.375f, 0.917f, -0.375f)
                        quadToRelative(0.541f, 0f, 0.916f, 0.375f)
                        quadToRelative(0.417f, 0.375f, 0.396f, 0.916f)
                        quadToRelative(-0.021f, 0.542f, -0.396f, 0.917f)
                        lineToRelative(-2.166f, 2.208f)
                        quadToRelative(-0.375f, 0.417f, -0.917f, 0.396f)
                        quadToRelative(-0.542f, -0.021f, -0.958f, -0.396f)
                        close()
                        moveTo(20f, 20f)
                        close()
                    }
                }.build()
            }
        }

        @Composable
        fun rememberDarkMode(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "dark_mode",
                    defaultWidth = 40.0.dp,
                    defaultHeight = 40.0.dp,
                    viewportWidth = 40.0f,
                    viewportHeight = 40.0f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero
                    ) {
                        moveTo(20f, 34.917f)
                        quadToRelative(-6.208f, 0f, -10.562f, -4.355f)
                        quadTo(5.083f, 26.208f, 5.083f, 20f)
                        quadToRelative(0f, -5.542f, 3.417f, -9.583f)
                        quadToRelative(3.417f, -4.042f, 8.833f, -5.042f)
                        quadToRelative(1.584f, -0.292f, 2.125f, 0.604f)
                        quadToRelative(0.542f, 0.896f, -0.166f, 2.438f)
                        quadToRelative(-0.417f, 0.958f, -0.625f, 1.979f)
                        quadToRelative(-0.209f, 1.021f, -0.209f, 2.104f)
                        quadToRelative(0f, 3.792f, 2.625f, 6.417f)
                        reflectiveQuadToRelative(6.417f, 2.625f)
                        quadToRelative(1.083f, 0f, 2.083f, -0.209f)
                        quadToRelative(1f, -0.208f, 1.959f, -0.583f)
                        quadToRelative(1.708f, -0.708f, 2.541f, -0.104f)
                        quadToRelative(0.834f, 0.604f, 0.5f, 2.271f)
                        quadToRelative(-0.958f, 5.125f, -4.979f, 8.562f)
                        quadToRelative(-4.021f, 3.438f, -9.604f, 3.438f)
                        close()
                        moveToRelative(0f, -2.625f)
                        quadToRelative(4.25f, 0f, 7.521f, -2.604f)
                        quadToRelative(3.271f, -2.605f, 4.271f, -6.271f)
                        quadToRelative(-1f, 0.375f, -2.104f, 0.583f)
                        quadToRelative(-1.105f, 0.208f, -2.188f, 0.208f)
                        quadToRelative(-4.875f, 0f, -8.292f, -3.416f)
                        quadToRelative(-3.416f, -3.417f, -3.416f, -8.292f)
                        quadToRelative(0f, -0.958f, 0.187f, -2.021f)
                        quadToRelative(0.188f, -1.062f, 0.646f, -2.354f)
                        quadToRelative(-3.875f, 1.208f, -6.396f, 4.5f)
                        reflectiveQuadTo(7.708f, 20f)
                        quadToRelative(0f, 5.125f, 3.584f, 8.708f)
                        quadToRelative(3.583f, 3.584f, 8.708f, 3.584f)
                        close()
                        moveToRelative(-0.208f, -12.084f)
                        close()
                    }
                }.build()
            }
        }
    """.trimIndent()

}