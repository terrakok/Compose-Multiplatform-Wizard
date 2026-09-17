rootProject.name = "compose-multiplatform-wizard"
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("kotlinWrappers") {
            val wrappersVersion = "2026.9.2"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}
