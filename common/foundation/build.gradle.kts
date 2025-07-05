import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dev.ksp)
    alias(libs.plugins.common.android.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.foundation"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // kotlin serialization
    api(libs.kotlin.serialization.json)
    api(libs.kotlin.serialization.protobuf)

    // koin
    api(libs.koin.core)
    api(libs.koin.coroutines)
    api(libs.koin.android)
    api(libs.koin.android.compat)
    api(libs.koin.annotations)

    ksp(libs.koin.ksp.compiler)
}