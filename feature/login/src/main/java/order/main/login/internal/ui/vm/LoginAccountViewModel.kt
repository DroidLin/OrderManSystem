package order.main.login.internal.ui.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import order.main.foundation.AndroidContext
import order.main.foundation.ioScope
import order.main.foundation.runOnIO
import order.main.login.R
import order.main.login.internal.repositories.LoginRepository
import order.main.login.internal.ui.data.InputExtras
import order.main.login.internal.ui.data.LoginAccountPasswordSideEffectState
import order.main.login.internal.ui.data.LoginAccountPasswordUiState
import order.main.login.ui.route.LoginAccountScreenRoute
import order.main.user.UserAccountLocalRepository
import order.main.user.UserInfoLocalRepository
import order.main.user.qualifier.MyUserDataRepository
import order.main.user.qualifier.MyUserInfoRepository
import org.koin.android.annotation.KoinViewModel

@SuppressLint("StaticFieldLeak")
@KoinViewModel
internal class LoginAccountViewModel(
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

    private val _uiState = MutableStateFlow(
        value = LoginAccountPasswordUiState(
            inputAccount = TextFieldValue(
                text = loginAccountScreenRoute.inputAccount ?: ""
            )
        )
    )

    val uiState = this._uiState.asStateFlow()

    val sideEffectState = this._uiState
        .map { uiState -> uiState.toSideEffect(this.context) }
        .stateIn(ioScope, SharingStarted.WhileSubscribed(), LoginAccountPasswordSideEffectState())

    fun updateInputAccount(value: TextFieldValue) {
        this._uiState.update { it.copy(inputAccount = value) }
    }

    fun updateInputPassword(value: TextFieldValue) {
        this._uiState.update { it.copy(inputPassword = value) }
    }

    fun doLogin(showNotification: (String) -> Unit) {
        val doLoginEnabled = this.sideEffectState.value.doLoginButtonEnabled
        if (!doLoginEnabled) {
            return
        }
        val inputPhone = this._uiState.value.inputAccount.text
        val inputPassword = this._uiState.value.inputPassword.text

        this._uiState.update { it.copy(isLoading = true) }
        runOnIO {
            val ret = this.loginRepository.loginPassword(inputPhone, inputPassword)
            delay(2000L)
            if (ret.isFailure) {
                val message = ret.exceptionOrNull()?.message
                if (!message.isNullOrEmpty()) {
                    showNotification(message)
                }
                return@runOnIO
            }
            val userAccount = ret.getOrNull()?.userAccount
            val userInfo = ret.getOrNull()?.userInfo
            if (userAccount == null || userInfo == null) {
                val message = this.context.getString(
                    R.string.login_string_app_internal_error,
                    "userAccount: $userAccount, userInfo: $userInfo"
                )
                showNotification(message)
                return@runOnIO
            }
            // finish login
            this.userInfoLocalRepository.updateUserInfo(userInfo)
            this.userAccountLocalRepository.updateUserAccount(userAccount)
        }.invokeOnCompletion { this._uiState.update { it.copy(isLoading = false) } }
    }

}

private fun LoginAccountPasswordUiState.toSideEffect(context: Context): LoginAccountPasswordSideEffectState {
    val uiState = this

    val accountText = uiState.inputAccount.text
    val inputAccountExtras = if (accountText.isNotEmpty()) {
        var isError = false
        var message: String? = null
        if (accountText.length < 4) {
            isError = true
            message = context.getString(R.string.login_string_account_input_limit)
        }
        InputExtras(isError = isError, message = message)
    } else null

    val passwordText = uiState.inputPassword.text
    val inputPasswordExtras = if (passwordText.isNotEmpty()) {
        var isError = false
        var message: String? = null
        when {
            passwordText.length < 8 -> {
                isError = true
                message = context.getString(R.string.string_login_password_at_least_8)
            }

            else -> null
        }
        InputExtras(isError = isError, message = message)
    } else null
    val doLoginButtonEnabled = !uiState.isLoading && passwordText.length >= 8
    return LoginAccountPasswordSideEffectState(
        doLoginButtonEnabled = doLoginButtonEnabled,
        inputAccountExtras = inputAccountExtras,
        inputPasswordExtras = inputPasswordExtras
    )
}