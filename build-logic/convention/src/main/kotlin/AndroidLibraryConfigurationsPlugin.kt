import com.android.build.gradle.LibraryExtension
import order.man.build.gradle.convention.ProjectConfig
import order.man.build.gradle.convention.getPlugin
import order.man.build.gradle.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConfigurationsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(ProjectConfig) {
            with(project) {
                apply(plugin = libs.getPlugin("android.library").pluginId)
                apply(plugin = libs.getPlugin("kotlin.android").pluginId)
                extensions.findByType<LibraryExtension>()?.apply {
                    compileSdk = projectCompileVersion
                    defaultConfig {
                        minSdk = projectMinSdkVersion

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
                        sourceCompatibility = ProjectConfig.javaVersion
                        targetCompatibility = ProjectConfig.javaVersion
                    }
                }
                extensions.getByType<KotlinAndroidProjectExtension>().apply {
                    compilerOptions {
                        jvmTarget.set(ProjectConfig.jvmTarget)
                    }
                }
                junitDependencies()
            }
        }
    }

}