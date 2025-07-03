package order.main.system

import order.main.database.DatabaseModule
import order.main.datastore.DataStoreModule
import order.main.foundation.AppFoundationModule
import order.main.network.NetworkApiModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        AppFoundationModule::class,
        DatabaseModule::class,
        DataStoreModule::class,
        NetworkApiModule::class,
    ]
)
class AppLauncherModules