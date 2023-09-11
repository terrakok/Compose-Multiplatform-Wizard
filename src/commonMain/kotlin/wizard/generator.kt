package wizard

import wizard.files.Gitignore
import wizard.files.GradleBat
import wizard.files.GradleLibsVersion
import wizard.files.GradleProperties
import wizard.files.GradleWrapperJar
import wizard.files.GradleWrapperProperties
import wizard.files.Gradlew
import wizard.files.Readme
import wizard.files.RootBuildGradleKts
import wizard.files.SettingsGradleKts
import wizard.files.app.AndroidAppKt
import wizard.files.app.AndroidManifest
import wizard.files.app.AppKt
import wizard.files.app.BrowserAppKt
import wizard.files.app.BrowserMainKt
import wizard.files.app.ColorKt
import wizard.files.app.DesktopAppKt
import wizard.files.app.DesktopMainKt
import wizard.files.app.GraphQLQuery
import wizard.files.app.GraphQLSchema
import wizard.files.app.IconsKt
import wizard.files.app.IndexHtml
import wizard.files.app.IosAccentColor
import wizard.files.app.IosAppIcon
import wizard.files.app.IosAppKt
import wizard.files.app.IosAppSwift
import wizard.files.app.IosAssets
import wizard.files.app.IosMainKt
import wizard.files.app.IosPbxproj
import wizard.files.app.IosPreviewAssets
import wizard.files.app.IosXcworkspace
import wizard.files.app.ModuleBuildGradleKts
import wizard.files.app.ThemeKt

fun ProjectInfo.buildFiles() = buildList {
    add(Gitignore())
    add(Readme(this@buildFiles))

    add(GradleBat())
    add(Gradlew())
    add(GradleWrapperProperties(this@buildFiles))
    add(GradleWrapperJar())
    add(GradleLibsVersion(this@buildFiles))

    add(GradleProperties())
    add(RootBuildGradleKts(this@buildFiles))
    add(SettingsGradleKts(this@buildFiles))

    add(ModuleBuildGradleKts(this@buildFiles))
    add(ColorKt(this@buildFiles))
    add(ThemeKt(this@buildFiles))
    add(AppKt(this@buildFiles))
    add(IconsKt(this@buildFiles))

    if (this@buildFiles.dependencies.contains(ApolloPlugin)) {
        add(GraphQLSchema())
        add(GraphQLQuery())
    }

    if (this@buildFiles.hasAndroid) {
        add(AndroidManifest(this@buildFiles))
        add(AndroidAppKt(this@buildFiles))
    }

    if (this@buildFiles.hasDesktop) {
        add(DesktopAppKt(this@buildFiles))
        add(DesktopMainKt(this@buildFiles))
    }

    if (this@buildFiles.hasIos) {
        add(IosAppKt(this@buildFiles))
        add(IosMainKt(this@buildFiles))

        add(IosAppIcon())
        add(IosAccentColor())
        add(IosAssets())
        add(IosPreviewAssets())
        add(IosAppSwift())
        add(IosXcworkspace())
        add(IosPbxproj(this@buildFiles))
    }

    if (this@buildFiles.hasBrowser) {
        add(BrowserAppKt(this@buildFiles))
        add(IndexHtml(this@buildFiles))
        add(BrowserMainKt(this@buildFiles))
    }
}