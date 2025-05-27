plugins {
    alias(libs.plugins.multiplatform)
    id("convention.publication")
}

group = "org.desktop.app"
version = "1.0.0"

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

}
