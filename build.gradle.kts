plugins {
    kotlin("multiplatform").version("1.8.10")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        browser {
            testTask {
                enabled = false
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.518")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.518")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.6-pre.518")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.9.1-pre.518")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.10.9-pre.518")

                implementation(npm("file-saver", "2.0.5"))
                implementation(npm("jszip", "3.10.1"))
                implementation(npm("stream", "0.0.2"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

task<Copy>("fixMissingJvmResources") {
    dependsOn("jvmProcessResources")
    tasks.findByPath("jvmTest")?.dependsOn(this)

    from("$buildDir/processedResources/jvm/main")
    into("$buildDir/resources/")
}
