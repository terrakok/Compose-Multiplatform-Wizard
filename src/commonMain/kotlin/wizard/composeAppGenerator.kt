package wizard

import wizard.dependencies.ApolloPlugin
import wizard.files.*
import wizard.files.composeApp.*

fun ProjectInfo.generateComposeAppFiles(): List<ProjectFile> = buildList {
    val info = this@generateComposeAppFiles

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
    add(ColorKt(info))
    add(ThemeKt(info))
    add(AppKt(info))
    add(ComposeTestKt(info))

    add(IcCycloneXml(info))
    add(IcDarkModeXml(info))
    add(IcLightModeXml(info))
    add(IcRotateRightXml(info))
    add(StringsXml(info))
    add(IndieFlowerTtf(info))

    if (info.dependencies.contains(ApolloPlugin)) {
        add(GraphQLSchema(info))
        add(GraphQLQuery(info))
    }

    if (info.hasPlatform(ProjectPlatform.Android)) {
        add(AndroidManifest(info))
        addAll(AndroidAppIcons(info))
        add(AndroidAppKt(info))
        add(AndroidThemeKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Jvm)) {
        add(DesktopMainKt(info))
        add(DesktopThemeKt(info))
        addAll(DesktopAppIcons(info))
    }

    if (info.hasPlatform(ProjectPlatform.Ios)) {
        add(IosMainKt(info))
        add(IosThemeKt(info))

        addAll(IosAppIcons())
        add(IosAccentColor())
        add(IosAssets())
        add(IosPreviewAssets())
        add(IosAppSwift())
        add(IosXcworkspace())
        add(IosPbxproj(info))
        add(IosInfoPlist())
    }

    if (info.hasPlatform(ProjectPlatform.Js)) {
        add(JsIndexHtml(info))
        add(JsMainKt(info))
        add(JsThemeKt(info))
        add(JsFavicon(info))
    }

    if (info.hasPlatform(ProjectPlatform.Wasm)) {
        add(WasmJsIndexHtml(info))
        add(WasmJsMainKt(info))
        add(WasmJsThemeKt(info))
        add(WasmJsFavicon(info))
    }
}