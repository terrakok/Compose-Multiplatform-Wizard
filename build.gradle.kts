plugins {
    kotlin("multiplatform").version("1.9.21")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js {
        browser {
            testTask {
                enabled = false
            }
        }
        binaries.executable()
    }
    sourceSets {
        jsMain.dependencies {
            implementation(dependencies.enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.666"))
            implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-material")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons-material")

            implementation(npm("file-saver", "2.0.5"))
            implementation(npm("jszip", "3.10.1"))
            implementation(npm("stream", "0.0.2"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

task<Copy>("fixMissingJvmResources") {
    dependsOn("jvmProcessResources")
    tasks.findByPath("jvmTest")?.dependsOn(this)
    
    from(project.layout.buildDirectory.dir("processedResources/jvm/main"))
    into(project.layout.buildDirectory.dir("resources"))
}
