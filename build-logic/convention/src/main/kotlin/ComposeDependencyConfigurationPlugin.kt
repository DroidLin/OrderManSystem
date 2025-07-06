import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import order.man.build.gradle.convention.getLibrary
import order.man.build.gradle.convention.getPlugin
import order.man.build.gradle.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposeDependencyConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.getPlugin("compose.compiler").pluginId)
            extensions.findByType<BaseAppModuleExtension>()?.apply {
                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.15"
                }
                buildFeatures {
                    compose = true
                }
            }
            extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
                reportsDestination.value(layout.buildDirectory.dir("compose_compiler"))
                metricsDestination.value(layout.buildDirectory.dir("compose_compiler"))
                stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("stability_config.conf"))
            }
            dependencies {
                add("implementation", libs.getLibrary("androidx.activity.compose"))
                add("implementation", platform(libs.getLibrary("androidx.compose.bom")))
                add("androidTestImplementation", platform(libs.getLibrary("androidx.compose.bom")))
                add("implementation", libs.getLibrary("androidx.ui"))
                add("implementation", libs.getLibrary("androidx.ui.graphics"))
                add("implementation", libs.getLibrary("androidx.ui.tooling.preview"))
                add("implementation", libs.getLibrary("androidx.material3"))
                add("implementation", libs.getLibrary("androidx.material.icon.extended"))
                add("implementation", libs.getLibrary("androidx.lifecycle.runtime.ktx"))
            }
        }
    }
}