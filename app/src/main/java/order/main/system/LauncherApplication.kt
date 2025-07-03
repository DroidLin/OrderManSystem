package order.main.system

import android.app.Application
import order.main.foundation.KoinFoundation
import org.koin.ksp.generated.module

class LauncherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinFoundation.initApplication(
            context = this,
            modules = arrayOf(AppLauncherModules().module)
        )
    }
}