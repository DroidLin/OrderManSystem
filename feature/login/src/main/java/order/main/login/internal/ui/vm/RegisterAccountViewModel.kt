package order.main.login.internal.ui.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import order.main.foundation.AndroidContext
import order.main.login.internal.repositories.LoginRepository
import order.main.login.ui.route.LoginAccountScreenRoute
import order.main.user.UserAccountLocalRepository
import order.main.user.UserInfoLocalRepository
import order.main.user.qualifier.MyUserDataRepository
import order.main.user.qualifier.MyUserInfoRepository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
@SuppressLint("StaticFieldLeak")
internal class RegisterAccountViewModel(
    private val savedStateHandle: SavedStateHandle,
    @param:AndroidContext
    private val context: Context,
    @param:MyUserDataRepository
    private val userAccountLocalRepository: UserAccountLocalRepository,
    @param:MyUserInfoRepository
    private val userInfoLocalRepository: UserInfoLocalRepository,
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val loginAccountScreenRoute: LoginAccountScreenRoute = savedStateHandle.toRoute()
}