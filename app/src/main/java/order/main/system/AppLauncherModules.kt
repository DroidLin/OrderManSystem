package order.main.system

import order.main.database.DatabaseModule
import order.main.datastore.DataStoreModule
import order.main.foundation.FoundationDependency
import order.main.login.ui.LoginModule
import order.main.network.NetworkApiDependency
import order.main.system.ui.AppLauncherViewModel
import order.main.user.UserAccountLocalRepository
import order.main.user.qualifier.MyUserDataRepository
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module(
    includes = [
        FoundationDependency::class,
        DatabaseModule::class,
        DataStoreModule::class,
        NetworkApiDependency::class,
        LoginModule::class
    ]
)
class AppLauncherModules {

    @KoinViewModel
    fun appLauncherViewModel(
        @MyUserDataRepository
        userAccountRepository: UserAccountLocalRepository
    ): AppLauncherViewModel = AppLauncherViewModel(userAccountRepository)
}