import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposeCompilerConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<BaseAppModuleExtension>()?.apply {
                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.15"
                }
            }
            extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
                reportsDestination.value(layout.buildDirectory.dir("compose_compiler"))
                metricsDestination.value(layout.buildDirectory.dir("compose_compiler"))
                stabilityConfigurationFiles.add(rootProject.layout.projectDirectory.file("stability_config.conf"))
            }
        }
    }
}