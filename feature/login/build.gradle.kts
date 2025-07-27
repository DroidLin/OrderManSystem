plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.compose.compiler.configuration)
}

android {
    namespace = "order.main.login"
}

dependencies {
    implementation(project(":common:foundation"))
    implementation(project(":common:foundation-ui"))
    implementation(project(":common:network"))
    implementation(project(":feature:user"))
}