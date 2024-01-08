package wizard

import wizard.files.*
import wizard.files.kmpLibrary.*
import wizard.files.kmpLibrary.convention.ConventionPluginsBuildGradleKts
import wizard.files.kmpLibrary.convention.ConventionPublicationGradleKts

fun ProjectInfo.generateKmpLibraryFiles() = buildList {
    val info = this@generateKmpLibraryFiles
    add(Gitignore())
    add(Readme(info))

    add(GradleBat())
    add(Gradlew())
    add(GradleWrapperProperties(info))
    add(GradleWrapperJar())
    add(GradleLibsVersion(info))

    add(GradleProperties(false))
    add(RootBuildGradleKts(info))
    add(SettingsGradleKts(info, true))

    add(ConventionPluginsBuildGradleKts(info))
    add(ConventionPublicationGradleKts(info))

    add(ModuleBuildGradleKts(info))

    add(FibonacciKt(info))
    add(FibonacciTestKt(info))
}