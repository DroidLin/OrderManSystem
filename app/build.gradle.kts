@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application.configuration)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.compose.compiler.configuration)
}

android {
    namespace = "order.main.system"
    defaultConfig {
        applicationId = "order.main.system"
    }
}

dependencies {
    implementation(libs.androidx.profileinstaller)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":common:foundation"))
    implementation(project(":common:foundation-ui"))
    implementation(project(":common:database"))
    implementation(project(":common:datastore"))
    implementation(project(":common:network"))

    implementation(project(":feature:login"))
    implementation(project(":feature:user"))
}