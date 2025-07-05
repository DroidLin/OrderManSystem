import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class KotlinKspConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.getByType<KspExtension>().apply {
                arg("KOIN_DEFAULT_MODULE", project.property("KoinDefaultModule").toString())
            }
        }
    }
}