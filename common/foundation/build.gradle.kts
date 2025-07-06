import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.foundation"
}

dependencies {
    // kotlin serialization
    api(libs.kotlin.serialization.json)
    api(libs.kotlin.serialization.protobuf)

    // koin
    api(libs.koin.core)
    api(libs.koin.coroutines)
    api(libs.koin.android)
    api(libs.koin.android.compat)
    api(libs.koin.annotations)
}