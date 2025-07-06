plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "order.main.network"
}

dependencies {
    implementation(project(":common:foundation"))
    implementation(project(":common:datastore"))

    // ktor
    api(libs.ktor.client.android)
    api(libs.ktor.client.serialization)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.ktor.serialization.kotlinx.protobuf)
}