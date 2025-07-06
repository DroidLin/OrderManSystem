package order.man.build.gradle.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object ProjectConfig {

    val Project.projectCompileVersion: Int get() = property("compileSdkVersion").toString().toInt()
    val Project.projectTargetSdkVersion: Int get() = property("targetSdkVersion").toString().toInt()
    val Project.projectMinSdkVersion: Int get() = property("minSdkVersion").toString().toInt()
    val Project.projectVersionCode: Int get() = property("versionCode").toString().toInt()
    val Project.projectVersionName: String get() = property("versionName").toString()

    val javaVersion = JavaVersion.VERSION_17
    val jvmTarget = JvmTarget.JVM_17

    fun Project.junitDependencies() {
        dependencies {
            // common junit dependencies
            add("testImplementation", libs.getLibrary("junit"))
            add("androidTestImplementation", libs.getLibrary("androidx.junit"))
            add("androidTestImplementation", libs.getLibrary("androidx.espresso.core"))
            add("androidTestImplementation", libs.getLibrary("androidx.ui.test.junit4"))
        }
    }
}