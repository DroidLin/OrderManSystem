package order.main.login

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.ktor.client.HttpClient
import order.main.foundation.AndroidContext
import order.main.login.repositories.LoginRepository
import order.main.login.repositories.MyLoginRepository
import order.main.login.ui.internal.datasource.LoginPhoneAndPasswordDataSource
import order.main.login.ui.internal.vm.LoginAccountViewModel
import order.main.user.UserAccountLocalRepository
import order.main.user.UserDependencies
import order.main.user.qualifier.MyUserDataRepository
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
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
        userAccountLocalRepository: UserAccountLocalRepository,
        loginRepository: LoginRepository
    ): LoginAccountViewModel = LoginAccountViewModel(
        savedStateHandle = savedStateHandle,
        context = context,
        loginAccountScreenRoute = savedStateHandle.toRoute(),
        userAccountLocalRepository = userAccountLocalRepository,
        loginRepository = loginRepository,
    )

    @Factory(binds = [LoginPhoneAndPasswordDataSource::class])
    internal fun loginPhoneAndPasswordDataSource(
        httpClient: HttpClient,
    ): LoginPhoneAndPasswordDataSource = LoginPhoneAndPasswordDataSource(httpClient)

    @Factory(binds = [LoginRepository::class])
    internal fun myLoginRepository(
        loginPhoneAndPasswordDataSource: LoginPhoneAndPasswordDataSource
    ): LoginRepository = MyLoginRepository(loginPhoneAndPasswordDataSource)
}