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
            
            ### Publish to MavenLocal
            1) Run `./gradlew :${info.moduleName}:publishToMavenLocal`
            2) Open `~/.m2/repository/${info.packageId.replace(".", "/")}/"}`

            ### Publish to MavenCentral

            1) Registering a Sonatype account as described here: 
               https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
            2) Add developer id, name, email and the project url to
               `/convention-plugins/src/main/kotlin/convention.publication.gradle.kts`
            3) Generate a GPG key:
               https://getstream.io/blog/publishing-libraries-to-mavencentral-2021/#generating-a-gpg-key-pair
               ```
               gpg --full-gen-key
               gpg --keyserver keyserver.ubuntu.com --send-keys XXXXXXXX
               gpg --export-secret-key XXXXXXXX > XXXXXXXX.gpg
               ```
            4) Add the secrets to `local.properties`:
               ```
               signing.keyId=XXXXXXXX
               signing.password=[key password]
               signing.secretKeyRingFile=../XXXXXXXX.gpg
               ossrhUsername=[Sonatype token_user]
               ossrhPassword=[Sonatype token_password]
               ```
            5) Run `./gradlew :${info.moduleName}:publishAllPublicationsToSonatypeRepository`

        """.trimIndent())
    }
}