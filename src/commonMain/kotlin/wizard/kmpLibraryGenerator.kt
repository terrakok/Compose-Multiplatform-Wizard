package wizard

import wizard.files.Gitignore
import wizard.files.GradleBat
import wizard.files.GradleLibsVersion
import wizard.files.GradleProperties
import wizard.files.GradleWrapperJar
import wizard.files.GradleWrapperProperties
import wizard.files.Gradlew
import wizard.files.RootBuildGradleKts
import wizard.files.SettingsGradleKts
import wizard.files.kmpLibrary.FibonacciKt
import wizard.files.kmpLibrary.FibonacciTestKt
import wizard.files.kmpLibrary.ModuleBuildGradleKts
import wizard.files.kmpLibrary.Readme
import wizard.files.kmpLibrary.sample.AndroidMainKt
import wizard.files.kmpLibrary.sample.AndroidManifestXml
import wizard.files.kmpLibrary.sample.ComposeAppKt
import wizard.files.kmpLibrary.sample.IosMainKt
import wizard.files.kmpLibrary.sample.JvmMainKt
import wizard.files.kmpLibrary.sample.SampleComposeAppBuildGradleKts
import wizard.files.kmpLibrary.sample.SampleIosXcodeInfoPlist
import wizard.files.kmpLibrary.sample.SampleIosXcodeIosAppSwift
import wizard.files.kmpLibrary.sample.SampleIosXcodePbxproj
import wizard.files.kmpLibrary.sample.TerminalAppBuildGradleKts
import wizard.files.kmpLibrary.sample.TerminalAppMainKt
import wizard.files.kmpLibrary.sample.WebIndexHtml
import wizard.files.kmpLibrary.sample.WebMainKt

fun ProjectInfo.generateKmpLibraryFiles() = buildList {
    val info = this@generateKmpLibraryFiles
    add(Gitignore())
    add(Readme(info))

    add(GradleBat())
    add(Gradlew())
    add(GradleWrapperProperties(info))
    add(GradleWrapperJar())
    add(GradleLibsVersion(info))

    add(GradleProperties(info))
    add(RootBuildGradleKts(info))
    add(SettingsGradleKts(info))

    add(ModuleBuildGradleKts(info))

    add(FibonacciKt(info))
    add(FibonacciTestKt(info))

    if (needComposeSample) {
        add(SampleComposeAppBuildGradleKts(info))
        add(ComposeAppKt(info))
        if (hasPlatform(ProjectPlatform.Android)) {
            add(AndroidMainKt())
            add(AndroidManifestXml())
        }
        if (hasPlatform(ProjectPlatform.Ios)) {
            add(IosMainKt())
            add(SampleIosXcodeInfoPlist())
            add(SampleIosXcodeIosAppSwift())
            add(SampleIosXcodePbxproj())
        }
        if (hasPlatform(ProjectPlatform.Jvm)) {
            add(JvmMainKt())
        }
        if (hasWebPlatform()) {
            add(WebMainKt())
            add(WebIndexHtml())
        }
    }

    if (needTerminalSample) {
        add(TerminalAppBuildGradleKts(info))
        add(TerminalAppMainKt(info))
    }
}