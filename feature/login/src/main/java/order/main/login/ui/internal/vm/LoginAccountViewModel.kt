package order.main.login.ui.internal.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import order.main.foundation.ioScope
import order.main.foundation.runOnIO
import order.main.login.R
import order.main.login.repositories.LoginRepository
import order.main.login.ui.LoginAccountScreenRoute
import order.main.login.ui.internal.model.InputExtras
import order.main.login.ui.internal.model.LoginAccountPasswordSideEffectState
import order.main.login.ui.internal.model.LoginAccountPasswordUiState
import order.main.user.UserAccountLocalRepository

@SuppressLint("StaticFieldLeak")
internal class LoginAccountViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val loginAccountScreenRoute: LoginAccountScreenRoute,
    private val userAccountLocalRepository: UserAccountLocalRepository,
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        value = LoginAccountPasswordUiState(
            inputAccount = TextFieldValue(
                text = loginAccountScreenRoute.inputAccount ?: ""
            )
        )
    )

    val uiState = this._uiState.asStateFlow()

    val sideEffectState = this._uiState
        .map { uiState ->
            val passwordText = uiState.inputPassword.text
            val inputPasswordExtras = if (passwordText.isNotEmpty()) {
                when {
                    passwordText.length < 8 -> InputExtras(
                        isError = true,
                        message = context.getString(R.string.string_login_password_at_least_8)
                    )

                    else -> null
                }
            } else null
            LoginAccountPasswordSideEffectState(
                doLoginButtonEnabled = !uiState.isLoading && passwordText.length >= 8,
                inputAccountExtras = null,
                inputPasswordExtras = inputPasswordExtras
            )
        }
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
        }.invokeOnCompletion { this._uiState.update { it.copy(isLoading = false) } }
    }

}
