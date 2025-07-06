plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.database"
}

dependencies {
    implementation(project(":common:foundation"))
}