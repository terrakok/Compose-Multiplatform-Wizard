package wizard.files.kmpLibrary

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.ProjectPlatform
import wizard.hasPlatform

class Readme(info: ProjectInfo) : ProjectFile {
    override val path = "README.MD"
    override val content = buildString {
        appendLine("""
            # ${info.name}

            Kotlin Multiplatform Library

            ### Publish to MavenCentral

            1) Registering a Sonatype account as described here: 
               https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
            2) Add developer id, name, email and the project url to
               `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
            3) Add the secrets to `local.properties`:

            ```
            signing.keyId=...
            signing.password=...
            signing.secretKeyRingFile=...
            ossrhUsername=...
            ossrhPassword=...
            ```

            4) Run `./gradlew :${info.moduleName}:publishAllPublicationsToSonatypeRepository`

            ### Build platform artifacts
        """.trimIndent())

        if (info.hasPlatform(ProjectPlatform.Android)) {
            appendLine("""
                
                #### Android aar

                - Run `./gradlew :${info.moduleName}:assembleRelease`
                - Output: `/${info.moduleName}/build/outputs/aar/${info.moduleName}-release.aar`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Jvm)) {
            appendLine("""
                
                #### JVM jar

                - Run `./gradlew :${info.moduleName}:jvmJar`
                - Output: `/${info.moduleName}/build/libs/${info.moduleName}-jvm-1.0.jar`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Ios)) {
            appendLine("""
                
                #### iOS Framework

                - Run `./gradlew :${info.moduleName}:linkReleaseFrameworkIosArm64`
                - Output: `/${info.moduleName}/build/bin/iosArm64/releaseFramework/${info.moduleName}.framework`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Js)) {
            appendLine("""
                
                #### JS file

                - Run `./gradlew :${info.moduleName}:jsBrowserProductionWebpack`
                - Output: `/${info.moduleName}/build/dist/js/productionExecutable/${info.moduleName}.js`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Macos)) {
            appendLine("""
                
                #### macOS Framework

                - Run `./gradlew :${info.moduleName}:linkReleaseFrameworkMacosArm64`
                - Output: `/${info.moduleName}/build/bin/macosArm64/releaseFramework/${info.moduleName}.framework`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Linux)) {
            appendLine("""
                
                #### Linux static library

                - Run `./gradlew :${info.moduleName}:linkReleaseStaticLinuxX64`
                - Output: `/${info.moduleName}/build/bin/linuxX64/releaseStatic/lib${info.moduleName}.a`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Mingw)) {
            appendLine("""
                
                #### Windows static library

                - Run `./gradlew :${info.moduleName}:linkReleaseStaticMingwX64`
                - Output: `/${info.moduleName}/build/bin/mingwX64/releaseStatic/lib${info.moduleName}.a`
            """.trimIndent())
        }

        if (info.hasPlatform(ProjectPlatform.Wasm)) {
            appendLine("""
                
                #### Wasm binary file

                - Run `./gradlew :${info.moduleName}:wasmJsBrowserDistribution`
                - Output: `/${info.moduleName}/build/dist/wasmJs/productionExecutable/${info.moduleName}-wasm-js.wasm`
            """.trimIndent())
        }
    }
}