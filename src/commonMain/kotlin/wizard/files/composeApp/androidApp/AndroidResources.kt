package wizard.files.composeApp.androidApp

import wizard.ProjectFile
import wizard.ProjectInfo

class AndroidManifest(info: ProjectInfo) : ProjectFile {
    override val path = "androidApp/src/main/AndroidManifest.xml"
    override val content = """
        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android">
        
            <application
                android:icon="@mipmap/ic_launcher"
                android:label="${info.name}"
                android:theme="@android:style/Theme.Material.NoActionBar">
                <activity
                    android:name=".AppActivity"
                    android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"
                    android:launchMode="singleInstance"
                    android:windowSoftInputMode="adjustPan"
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
