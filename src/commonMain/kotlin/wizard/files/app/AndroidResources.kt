package wizard.files.app

import wizard.ProjectFile
import wizard.ProjectInfo

class AndroidManifest : ProjectFile {
    override val path = "composeApp/src/androidMain/AndroidManifest.xml"
    override val content = """
        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android">
        
            <application
                android:name=".AndroidApp"
                android:icon="@android:drawable/ic_menu_compass"
                android:label="@string/app_name"
                android:theme="@style/Theme.Splash">
                <activity
                    android:name=".AppActivity"
                    android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"
                    android:launchMode="singleInstance"
                    android:windowSoftInputMode="adjustResize"
                    android:exported="true">
                    <intent-filter>
                        <action android:name="android.intent.action.MAIN" />
                        <category android:name="android.intent.category.LAUNCHER" />
                    </intent-filter>
                </activity>
            </application>
        
        </manifest>
    """.trimIndent()
}

class AndroidThemesXml : ProjectFile {
    override val path = "composeApp/src/androidMain/resources/values/themes.xml"
    override val content = """
        <resources>
            <style name="Theme.Splash" parent="Theme.AppCompat.DayNight.NoActionBar">
                <item name="android:windowTranslucentNavigation">true</item>
                <item name="android:windowTranslucentStatus">true</item>
            </style>
        </resources>
    """.trimIndent()
}

class AndroidStringsXml(info: ProjectInfo) : ProjectFile {
    override val path = "composeApp/src/androidMain/resources/values/strings.xml"
    override val content = """
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
            <string name="app_name">${info.name}</string>
        </resources>
    """.trimIndent()
}