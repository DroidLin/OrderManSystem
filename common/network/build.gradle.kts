plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.network"
}

dependencies {
    implementation(project(":common:foundation"))
    implementation(project(":common:datastore"))

    // ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.kotlinx.protobuf)
}