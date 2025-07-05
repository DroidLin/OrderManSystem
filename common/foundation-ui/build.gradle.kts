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
    namespace = "order.main.foundation.ui"
}

dependencies {
    api(libs.androidx.material3.android)
    testApi(libs.junit)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)

    // compose
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    androidTestApi(platform(libs.androidx.compose.bom))

    // ui tooling
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)

    // junit
    testApi(libs.junit)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)
    androidTestApi(libs.androidx.ui.test.junit4)

    // koin
    api(libs.koin.compose)
    api(libs.koin.androidx.compose)
    ksp(libs.koin.ksp.compiler)

    api(libs.kotlin.immutable.collection)

    // navigation
    api(libs.jetpack.navigation)

    implementation(project(":common:foundation"))
}