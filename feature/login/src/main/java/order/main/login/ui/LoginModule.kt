package order.main.login.ui

import order.main.login.ui.internal.vm.LoginAccountViewModel
import order.main.user.UserDependencies
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module

@Module(
    includes = [
        UserDependencies::class
    ]
)
class LoginModule {

    @KoinViewModel
    internal fun loginAccountViewModel(

    ): LoginAccountViewModel = LoginAccountViewModel()
}
