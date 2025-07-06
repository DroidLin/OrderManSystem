plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.compose.compiler.configuration)
}

android {
    namespace = "order.main.user"
}

dependencies {
    implementation(project(":common:foundation"))
    implementation(project(":common:foundation-ui"))
    implementation(project(":common:datastore"))
}