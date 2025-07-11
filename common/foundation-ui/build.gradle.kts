import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.compose.compiler.configuration)
}

android {
    namespace = "order.main.foundation.ui"
}

dependencies {
//    api(libs.androidx.material3.android)

    // ui tooling
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)

    // koin
    api(libs.koin.compose)
    api(libs.koin.androidx.compose)

    api(libs.kotlin.immutable.collection)

    // navigation
    api(libs.jetpack.navigation)

    implementation(project(":common:foundation"))
}