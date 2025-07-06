package order.main.login.ui

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import order.main.foundation.AndroidContext
import order.main.login.ui.internal.vm.LoginAccountViewModel
import order.main.user.UserAccountLocalRepository
import order.main.user.UserDependencies
import order.main.user.qualifier.MyUserDataRepository
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
        savedStateHandle: SavedStateHandle,
        @AndroidContext
        context: Context,
        @MyUserDataRepository
        userAccountLocalRepository: UserAccountLocalRepository
    ): LoginAccountViewModel = LoginAccountViewModel(
        savedStateHandle = savedStateHandle,
        context = context,
        loginAccountScreenRoute = savedStateHandle.toRoute(),
        userAccountLocalRepository = userAccountLocalRepository
    )
}
