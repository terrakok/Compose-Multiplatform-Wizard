package wizard

import wizard.files.*
import wizard.files.kmpLibrary.*
import wizard.files.kmpLibrary.convention.ConventionPluginsBuildGradleKts
import wizard.files.kmpLibrary.convention.ConventionPublicationGradleKts
import wizard.files.kmpLibrary.sample.AndroidMainKt
import wizard.files.kmpLibrary.sample.AndroidManifestXml
import wizard.files.kmpLibrary.sample.ComposeAppKt
import wizard.files.kmpLibrary.sample.IosMainKt
import wizard.files.kmpLibrary.sample.JsIndexHtml
import wizard.files.kmpLibrary.sample.JvmMainKt
import wizard.files.kmpLibrary.sample.SampleComposeAppBuildGradleKts
import wizard.files.kmpLibrary.sample.SampleIosXcodeInfoPlist
import wizard.files.kmpLibrary.sample.SampleIosXcodeIosAppSwift
import wizard.files.kmpLibrary.sample.SampleIosXcodePbxproj
import wizard.files.kmpLibrary.sample.TerminalAppBuildGradleKts
import wizard.files.kmpLibrary.sample.TerminalAppMainKt
import wizard.files.kmpLibrary.sample.WebMainKt
import wizard.files.kmpLibrary.sample.WasmIndexHtml

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

    add(ConventionPluginsBuildGradleKts(info))
    add(ConventionPublicationGradleKts(info))

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
        if (hasPlatform(ProjectPlatform.Js)) {
            add(WebMainKt(false))
            add(JsIndexHtml())
        }
        if (hasPlatform(ProjectPlatform.Wasm)) {
            add(WebMainKt(true))
            add(WasmIndexHtml())
        }
    }

    if (needTerminalSample) {
        add(TerminalAppBuildGradleKts(info))
        add(TerminalAppMainKt(info))
    }
}