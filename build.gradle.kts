plugins {
    kotlin("multiplatform").version("1.8.10")
}

repositories {
    mavenCentral()
}

kotlin {
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
            }
        }
    }
}
