package order.main.system

import order.main.database.DatabaseModule
import order.main.datastore.DataStoreModule
import order.main.foundation.FoundationDependency
import order.main.network.NetworkApiDependency
import org.koin.core.annotation.Module

@Module(
    includes = [
        FoundationDependency::class,
        DatabaseModule::class,
        DataStoreModule::class,
        NetworkApiDependency::class,
    ]
)
class AppLauncherModules