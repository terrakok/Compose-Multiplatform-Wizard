package wizard.files.composeApp.shared

import wizard.*

class IcCycloneXml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/drawable/ic_cyclone.xml"
    override val content = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24">
            <path
                android:fillColor="#000000"
                android:pathData="M12,8c-2.21,0 -4,1.79 -4,4c0,2.21 1.79,4 4,4c2.21,0 4,-1.79 4,-4C16,9.79 14.21,8 12,8zM12,14c-1.1,0 -2,-0.9 -2,-2c0,-1.1 0.9,-2 2,-2s2,0.9 2,2C14,13.1 13.1,14 12,14z" />
            <path
                android:fillColor="#000000"
                android:pathData="M22,7.47V5.35C20.05,4.77 16.56,4 12,4C9.85,4 7.89,4.86 6.46,6.24C6.59,5.39 6.86,3.84 7.47,2H5.35C4.77,3.95 4,7.44 4,12c0,2.15 0.86,4.11 2.24,5.54c-0.85,-0.14 -2.4,-0.4 -4.24,-1.01v2.12C3.95,19.23 7.44,20 12,20c2.15,0 4.11,-0.86 5.54,-2.24c-0.14,0.85 -0.4,2.4 -1.01,4.24h2.12C19.23,20.05 20,16.56 20,12c0,-2.15 -0.86,-4.11 -2.24,-5.54C18.61,6.59 20.16,6.86 22,7.47zM12,18c-3.31,0 -6,-2.69 -6,-6s2.69,-6 6,-6s6,2.69 6,6S15.31,18 12,18z" />
        </vector>
    """.trimIndent()
}

class IcDarkModeXml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/drawable/ic_dark_mode.xml"
    override val content = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24">
            <path
                android:fillColor="#000000"
                android:pathData="M12,3c-4.97,0 -9,4.03 -9,9s4.03,9 9,9s9,-4.03 9,-9c0,-0.46 -0.04,-0.92 -0.1,-1.36c-0.98,1.37 -2.58,2.26 -4.4,2.26c-2.98,0 -5.4,-2.42 -5.4,-5.4c0,-1.81 0.89,-3.42 2.26,-4.4C12.92,3.04 12.46,3 12,3L12,3z" />
        </vector>
    """.trimIndent()
}

class IcLightModeXml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/drawable/ic_light_mode.xml"
    override val content = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:viewportWidth="24"
            android:viewportHeight="24">
            <path
                android:fillColor="#000000"
                android:pathData="M12,7c-2.76,0 -5,2.24 -5,5s2.24,5 5,5s5,-2.24 5,-5S14.76,7 12,7L12,7zM2,13l2,0c0.55,0 1,-0.45 1,-1s-0.45,-1 -1,-1l-2,0c-0.55,0 -1,0.45 -1,1S1.45,13 2,13zM20,13l2,0c0.55,0 1,-0.45 1,-1s-0.45,-1 -1,-1l-2,0c-0.55,0 -1,0.45 -1,1S19.45,13 20,13zM11,2v2c0,0.55 0.45,1 1,1s1,-0.45 1,-1V2c0,-0.55 -0.45,-1 -1,-1S11,1.45 11,2zM11,20v2c0,0.55 0.45,1 1,1s1,-0.45 1,-1v-2c0,-0.55 -0.45,-1 -1,-1C11.45,19 11,19.45 11,20zM5.99,4.58c-0.39,-0.39 -1.03,-0.39 -1.41,0c-0.39,0.39 -0.39,1.03 0,1.41l1.06,1.06c0.39,0.39 1.03,0.39 1.41,0s0.39,-1.03 0,-1.41L5.99,4.58zM18.36,16.95c-0.39,-0.39 -1.03,-0.39 -1.41,0c-0.39,0.39 -0.39,1.03 0,1.41l1.06,1.06c0.39,0.39 1.03,0.39 1.41,0c0.39,-0.39 0.39,-1.03 0,-1.41L18.36,16.95zM19.42,5.99c0.39,-0.39 0.39,-1.03 0,-1.41c-0.39,-0.39 -1.03,-0.39 -1.41,0l-1.06,1.06c-0.39,0.39 -0.39,1.03 0,1.41s1.03,0.39 1.41,0L19.42,5.99zM7.05,18.36c0.39,-0.39 0.39,-1.03 0,-1.41c-0.39,-0.39 -1.03,-0.39 -1.41,0l-1.06,1.06c-0.39,0.39 -0.39,1.03 0,1.41s1.03,0.39 1.41,0L7.05,18.36z" />
        </vector>
    """.trimIndent()
}

class IcRotateRightXml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/drawable/ic_rotate_right.xml"
    override val content = """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="24dp"
            android:height="24dp"
            android:autoMirrored="true"
            android:viewportWidth="24"
            android:viewportHeight="24">
            <path
                android:fillColor="#000000"
                android:pathData="M15.55,5.55L11,1v3.07C7.06,4.56 4,7.92 4,12s3.05,7.44 7,7.93v-2.02c-2.84,-0.48 -5,-2.94 -5,-5.91s2.16,-5.43 5,-5.91L11,10l4.55,-4.45zM19.93,11c-0.17,-1.39 -0.72,-2.73 -1.62,-3.89l-1.42,1.42c0.54,0.75 0.88,1.6 1.02,2.47h2.02zM13,17.9v2.02c1.39,-0.17 2.74,-0.71 3.9,-1.61l-1.44,-1.44c-0.75,0.54 -1.59,0.89 -2.46,1.03zM16.89,15.48l1.42,1.41c0.9,-1.16 1.45,-2.5 1.62,-3.89h-2.02c-0.14,0.87 -0.48,1.72 -1.02,2.48z" />
        </vector>
    """.trimIndent()
}

class StringsXml(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/values/strings.xml"
    override val content = """
        <resources>
            <string name="cyclone">Cyclone</string>
            <string name="open_github">Open github</string>
            <string name="run">Run</string>
            <string name="stop">Stop</string>
            <string name="theme">Theme</string>
        </resources>
    """.trimIndent()
}

class IndieFlowerTtf(info: ProjectInfo) : BinaryFile {
    override val path = "${info.moduleName}/src/commonMain/composeResources/font/IndieFlower-Regular.ttf"
    override val resourcePath = "IndieFlower-Regular"
}