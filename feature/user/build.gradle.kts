import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.dev.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.common.android.configuration)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.compose.compiler.configuration)
}

android {
    namespace = "order.main.user"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":common:foundation"))
    implementation(project(":common:foundation-ui"))
    implementation(project(":common:datastore"))

    // koin ksp compiler
    ksp(libs.koin.ksp.compiler)
}