package wizard.files

import wizard.ProjectFile
import wizard.ProjectInfo

class GradleWrapperProperties(info: ProjectInfo) : ProjectFile {
    override val path = "gradle/wrapper/gradle-wrapper.properties"
    override val content = """
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-${info.gradleVersion}-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
""".trimStart()
}

class GradleWrapperJar : ProjectFile {
    override val path = "gradle/wrapper/gradle-wrapper.jar"
    override val content = "/* binary file */"
}