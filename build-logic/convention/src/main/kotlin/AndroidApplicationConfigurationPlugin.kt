import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import order.man.build.gradle.convention.ProjectConfig
import order.man.build.gradle.convention.getPlugin
import order.man.build.gradle.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(ProjectConfig) {
            with(target) {
                apply(plugin = libs.getPlugin("android.application").pluginId)
                apply(plugin = libs.getPlugin("kotlin.android").pluginId)
                extensions.findByType<BaseAppModuleExtension>()?.apply {
                    compileSdk = projectCompileVersion
                    defaultConfig {
                        minSdk = projectMinSdkVersion
                        targetSdk = projectTargetSdkVersion
                        versionCode = projectVersionCode
                        versionName = projectVersionName
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