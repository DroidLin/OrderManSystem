import com.google.devtools.ksp.gradle.KspExtension
import order.man.build.gradle.convention.getLibrary
import order.man.build.gradle.convention.getPlugin
import order.man.build.gradle.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KotlinKspConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.getPlugin("google.dev.ksp").pluginId)
            extensions.getByType<KspExtension>().apply {
                arg("KOIN_DEFAULT_MODULE", project.property("KoinDefaultModule").toString())
            }
            dependencies {
                add("ksp", libs.getLibrary("koin.ksp.compiler"))
            }
        }
    }
}