plugins {
    alias(libs.plugins.android.library.configuration)
    alias(libs.plugins.ksp.configuration)
}

android {
    namespace = "order.main.datastore"
}

dependencies {
    implementation(project(":common:foundation"))

    api(libs.datastore)
    api(libs.datastore.preferences)
}