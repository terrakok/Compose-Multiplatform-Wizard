package wizard

import wizard.files.*
import wizard.files.app.*

fun ProjectInfo.buildFiles() = buildList {
    add(Gitignore())
    add(Readme(this@buildFiles))

    add(GradleBat())
    add(Gradlew())
    add(GradleWrapperProperties(this@buildFiles))
    add(GradleWrapperJar())

    add(GradleProperties())
    add(RootBuildGradleKts(this@buildFiles))
    add(SettingsGradleKts(this@buildFiles))

    add(ModuleBuildGradleKts(this@buildFiles))
    add(AppThemeKt(this@buildFiles))
    add(AppKt(this@buildFiles))

    if (this@buildFiles.dependencies.contains(ApolloPlugin)) {
        add(GraphQLSchema())
        add(GraphQLQuery())
    }

    if (this@buildFiles.hasAndroid) {
        add(AndroidManifest())
        add(AndroidThemesXml())
        add(AndroidStringsXml(this@buildFiles))
        add(AndroidAppKt(this@buildFiles))
    }

    if (this@buildFiles.hasDesktop) {
        add(DesktopAppKt(this@buildFiles))
        add(DesktopMainKt(this@buildFiles))
    }

    if (this@buildFiles.hasIos) {
        add(Podspec())
        add(IosAppKt(this@buildFiles))
        add(IosMainKt(this@buildFiles))

        add(Podfile())
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