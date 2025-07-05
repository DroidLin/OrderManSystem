import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class CommonAndroidConfigurationsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            println("install CommonAndroidConfigurationsPlugin")
            extensions.findByType<BaseAppModuleExtension>()?.apply {
                compileSdk = property("compileSdkVersion").toString().toInt()
                defaultConfig {
                    minSdk = project.property("minSdkVersion").toString().toInt()
                    targetSdk = project.property("targetSdkVersion").toString().toInt()
                    versionCode = project.property("versionCode").toString().toInt()
                    versionName = project.property("versionName").toString()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
            }
            extensions.findByType<LibraryExtension>()?.apply {
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
            }
            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

}