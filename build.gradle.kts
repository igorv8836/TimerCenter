plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.conventional.commits) apply true
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.dokka) apply true
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
        enabled = false
    }
}

conventionalCommits {
    scopes = listOf(
        "main",
        "common",
        "database",
        "datastore",
        "ui_theme",
    )
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(file("docs"))
}