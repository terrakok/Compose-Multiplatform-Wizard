package wizard

import wizard.dependencies.ApolloPlugin
import wizard.files.Gitignore
import wizard.files.GradleBat
import wizard.files.GradleLibsVersion
import wizard.files.GradleProperties
import wizard.files.GradleWrapperJar
import wizard.files.GradleWrapperProperties
import wizard.files.Gradlew
import wizard.files.LocalProperties
import wizard.files.RootBuildGradleKts
import wizard.files.composeApp.GraphQLQuery
import wizard.files.composeApp.GraphQLSchema
import wizard.files.composeApp.Readme
import wizard.files.composeApp.SettingsGradleKts
import wizard.files.composeApp.androidApp.AndroidAppActivityKt
import wizard.files.composeApp.androidApp.AndroidAppBuildGradleKts
import wizard.files.composeApp.androidApp.AndroidAppIcons
import wizard.files.composeApp.androidApp.AndroidManifest
import wizard.files.composeApp.desktop.DesktopAppIcons
import wizard.files.composeApp.desktop.DesktopBuildGradleKts
import wizard.files.composeApp.desktop.DesktopMainKt
import wizard.files.composeApp.iosApp.IosAccentColor
import wizard.files.composeApp.iosApp.IosAppIcons
import wizard.files.composeApp.iosApp.IosAppSwift
import wizard.files.composeApp.iosApp.IosAssets
import wizard.files.composeApp.iosApp.IosInfoPlist
import wizard.files.composeApp.iosApp.IosPbxproj
import wizard.files.composeApp.iosApp.IosPreviewAssets
import wizard.files.composeApp.iosApp.IosXcworkspace
import wizard.files.composeApp.shared.AppKt
import wizard.files.composeApp.shared.ColorKt
import wizard.files.composeApp.shared.ComposeTestKt
import wizard.files.composeApp.shared.IcCycloneXml
import wizard.files.composeApp.shared.IcDarkModeXml
import wizard.files.composeApp.shared.IcLightModeXml
import wizard.files.composeApp.shared.IcRotateRightXml
import wizard.files.composeApp.shared.IndieFlowerTtf
import wizard.files.composeApp.shared.IosMainKt
import wizard.files.composeApp.shared.SharedBuildGradleKts
import wizard.files.composeApp.shared.StringsXml
import wizard.files.composeApp.shared.ThemeKt
import wizard.files.composeApp.webApp.WebBuildGradleKts
import wizard.files.composeApp.webApp.WebFavIcons
import wizard.files.composeApp.webApp.WebIndexHtml
import wizard.files.composeApp.webApp.WebMainKt
import wizard.files.composeApp.webApp.WebManifestJson

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

    add(SharedBuildGradleKts(info))
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
        add(LocalProperties())
        add(AndroidAppBuildGradleKts(info))
        add(AndroidManifest(info))
        addAll(AndroidAppIcons(info))
        add(AndroidAppActivityKt(info))
    }

    if (info.hasPlatform(ProjectPlatform.Jvm)) {
        add(DesktopBuildGradleKts(info))
        add(DesktopMainKt(info))
        addAll(DesktopAppIcons(info))
    }

    if (info.hasPlatform(ProjectPlatform.Ios)) {
        add(IosMainKt(info))

        addAll(IosAppIcons())
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
        addAll(WebFavIcons(info))
    }
}