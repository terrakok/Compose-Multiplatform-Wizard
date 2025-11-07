package wizard.files

import wizard.BinaryFile
import wizard.ProjectFile
import wizard.ProjectInfo

class GradleWrapperProperties(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/wrapper/gradle-wrapper.properties"
    override val content = """
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-${info.gradleVersion}-bin.zip
distributionSha256Sum=${info.gradleVersionSha}
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
""".trimStart()
}

class GradleWrapperJar : BinaryFile {
    override val path = "gradle/wrapper/gradle-wrapper.jar"
    override val resourcePath = "gradle-wrapper"
}

class Gradlew : BinaryFile {
    override val path = "gradlew"
    override val resourcePath = "gradlew"
}

class GradlewBat : BinaryFile {
    override val path = "gradlew.bat"
    override val resourcePath = "gradlew.bat"
}