import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.dev.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "order.main.foundation.ui"
    compileSdk = project.property("compileSdkVersion").toString().toInt()

    defaultConfig {
        minSdk = project.property("minSdkVersion").toString().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

ksp {
    arg("KOIN_DEFAULT_MODULE", project.property("KoinDefaultModule").toString())
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
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