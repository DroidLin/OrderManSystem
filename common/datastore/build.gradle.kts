import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dev.ksp)
    alias(libs.plugins.common.android.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.datastore"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":common:foundation"))

    api(libs.datastore)
    api(libs.datastore.preferences)

    ksp(libs.koin.ksp.compiler)
}