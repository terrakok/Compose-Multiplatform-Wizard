plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(project(":lamba"))
        }

    }
}

