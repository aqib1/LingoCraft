rootProject.name = "LingoCraft"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.9.23"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}