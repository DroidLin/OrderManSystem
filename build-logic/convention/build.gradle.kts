import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "order.main.apps.build.logic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.tools)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

gradlePlugin {
    plugins {
        register("AndroidApplicationConfiguration") {
            id = libs.plugins.android.application.configuration.get().pluginId
            implementationClass = "AndroidApplicationConfigurationPlugin"
        }
        register("AndroidLibraryConfigurations") {
            id = libs.plugins.android.library.configuration.get().pluginId
            implementationClass = "AndroidLibraryConfigurationsPlugin"
        }
        register("KotlinKspConfiguration") {
            id = libs.plugins.ksp.configuration.get().pluginId
            implementationClass = "KotlinKspConfigurationPlugin"
        }
        register("ComposeDependencyConfigurationPlugin") {
            id = libs.plugins.compose.compiler.configuration.get().pluginId
            implementationClass = "ComposeDependencyConfigurationPlugin"
        }
    }
}