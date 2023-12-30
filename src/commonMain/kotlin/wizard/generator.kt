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
    add(GradleLibsVersion(this@buildFiles))

    add(GradleProperties())
    add(RootBuildGradleKts(this@buildFiles))
    add(SettingsGradleKts(this@buildFiles))

    add(ModuleBuildGradleKts(this@buildFiles))
    add(ColorKt(this@buildFiles))
    add(ThemeKt(this@buildFiles))
    add(AppKt(this@buildFiles))

    if (this@buildFiles.dependencies.contains(ApolloPlugin)) {
        add(GraphQLSchema())
        add(GraphQLQuery())
    }

    if (this@buildFiles.hasPlatform(ComposePlatform.Android)) {
        add(AndroidManifest(this@buildFiles))
        add(AndroidAppKt(this@buildFiles))
        add(AndroidThemeKt(this@buildFiles))
    }

    if (this@buildFiles.hasPlatform(ComposePlatform.Jvm)) {
        add(DesktopAppKt(this@buildFiles))
        add(DesktopMainKt(this@buildFiles))
        add(DesktopThemeKt(this@buildFiles))
    }

    if (this@buildFiles.hasPlatform(ComposePlatform.Ios)) {
        add(IosAppKt(this@buildFiles))
        add(IosMainKt(this@buildFiles))
        add(IosThemeKt(this@buildFiles))

        add(IosAppIcon())
        add(IosAccentColor())
        add(IosAssets())
        add(IosPreviewAssets())
        add(IosAppSwift())
        add(IosXcworkspace())
        add(IosPbxproj(this@buildFiles))
    }

    if (this@buildFiles.hasPlatform(ComposePlatform.Browser)) {
        add(BrowserAppKt(this@buildFiles))
        add(IndexHtml(this@buildFiles))
        add(BrowserMainKt(this@buildFiles))
        add(BrowserThemeKt(this@buildFiles))
        add(WebPackConfig(this@buildFiles))
    }
}