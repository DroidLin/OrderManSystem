plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dev.ksp)
    alias(libs.plugins.common.android.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.database"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":common:foundation"))

    ksp(libs.koin.ksp.compiler)
}