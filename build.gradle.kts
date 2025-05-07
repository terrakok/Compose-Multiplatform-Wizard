plugins {
    kotlin("multiplatform").version("2.1.20")
    id("com.github.gmazzo.buildconfig").version("5.6.5")
    id("com.github.ben-manes.versions").version("0.52.0")
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
            implementation(kotlinWrappers.react)
            implementation(kotlinWrappers.reactDom)
            implementation(kotlinWrappers.emotion.styled)
            implementation(kotlinWrappers.mui.material)
            implementation(kotlinWrappers.mui.iconsMaterial)

            implementation(npm("file-saver", "2.0.5"))
            implementation(npm("jszip", "3.10.1"))
            implementation(npm("stream", "0.0.3"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("javax.json:javax.json-api:1.1.4")
            implementation("org.glassfish:javax.json:1.1.4")
        }
    }
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
    packageName("wizard")

    //-PwizardType=kmp
    when(findProperty("wizardType")) {
        "kmp" -> buildConfigField("wizard.WizardType", "wizardType", "WizardType.KmpLibrary")
        else -> buildConfigField("wizard.WizardType", "wizardType", "WizardType.ComposeApp")
    }
}

task<Copy>("fixMissingJvmResources") {
    dependsOn("jvmProcessResources")
    tasks.findByPath("jvmTest")?.dependsOn(this)
    
    from(project.layout.buildDirectory.dir("processedResources/jvm/main"))
    into(project.layout.buildDirectory.dir("resources"))
}
