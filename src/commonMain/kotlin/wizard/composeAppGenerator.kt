package wizard

import wizard.dependencies.ApolloPlugin
import wizard.files.*
import wizard.files.composeApp.AgentsMd
import wizard.files.composeApp.GraphQLQuery
import wizard.files.composeApp.GraphQLSchema
import wizard.files.composeApp.Readme
import wizard.files.composeApp.SettingsGradleKts
import wizard.files.composeApp.androidApp.AndroidAppActivityKt
import wizard.files.composeApp.androidApp.AndroidAppBuildGradleKts
import wizard.files.composeApp.androidApp.AndroidManifest
import wizard.files.composeApp.desktop.DesktopBuildGradleKts
import wizard.files.composeApp.desktop.DesktopMainKt
import wizard.files.composeApp.iosApp.*
import wizard.files.composeApp.shared.*
import wizard.files.composeApp.webApp.WebBuildGradleKts
import wizard.files.composeApp.webApp.WebIndexHtml
import wizard.files.composeApp.webApp.WebMainKt
import wizard.files.composeApp.webApp.WebManifestJson
import wizard.icon.AndroidAppIconRenderer
import wizard.icon.DesktopAppIconRenderer
import wizard.icon.IosAppIconRenderer
import wizard.icon.WebAppIconRenderer

fun ProjectInfo.generateComposeAppFiles(): List<ProjectFile> = buildList {
    val info = this@generateComposeAppFiles

    add(Gitignore())
    add(Readme(info))
    if (info.addAgentsMd) {
        add(AgentsMd(info))
    }

    add(GradlewBat())
    add(Gradlew())
    add(GradleWrapperProperties(info))
    add(GradleWrapperJar())
    add(GradleLibsVersion(info))

    add(GradleProperties(info))
    add(RootBuildGradleKts(info))
    add(SettingsGradleKts(info))

    add(SharedBuildGradleKts(info))
    add(ColorKt(info))
    add(ThemeKt(info))
    add(AppKt(info))
    if (info.addTests) {
        add(ComposeTestKt(info))
    }

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
        add(LocalProperties())
        add(AndroidAppBuildGradleKts(info))
        add(AndroidManifest(info))
        addAll(AndroidAppIconRenderer.render(info.appIcon))
        add(AndroidAppActivityKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Jvm)) {
        add(DesktopBuildGradleKts(info))
        add(DesktopMainKt(info))
        addAll(DesktopAppIconRenderer.render(info.appIcon))
    }

    if (info.hasPlatform(ProjectPlatform.Ios)) {
        add(IosMainKt(info))

        addAll(IosAppIconRenderer.render(info.appIcon))
        add(IosAccentColor())
        add(IosAssets())
        add(IosPreviewAssets())
        add(IosAppSwift(info))
        add(IosXcworkspace())
        add(IosPbxproj(info))
        add(IosInfoPlist())
    }

    if (info.hasWebPlatform()) {
        add(WebBuildGradleKts(info))
        add(WebIndexHtml(info))
        add(WebManifestJson(info))
        add(WebMainKt(info))
        addAll(WebAppIconRenderer.render(info.appIcon))
    }
}