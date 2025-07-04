package order.main.login.ui.internal.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import order.main.foundation.ioScope
import order.main.login.R
import order.main.login.ui.internal.LoginAccountScreenRoute
import order.main.login.ui.internal.model.InputExtras
import order.main.login.ui.internal.model.LoginAccountPasswordSideEffectState
import order.main.login.ui.internal.model.LoginAccountPasswordUiState
import order.main.user.UserDataLocalRepository

@SuppressLint("StaticFieldLeak")
internal class LoginAccountViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val context: Context,
    private val loginAccountScreenRoute: LoginAccountScreenRoute,
    private val userDataLocalRepository: UserDataLocalRepository
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
                        errorMessage = context.getString(R.string.string_login_password_at_least_8)
                    )

                    else -> null
                }
            } else null
            LoginAccountPasswordSideEffectState(
                doLoginButtonEnabled = passwordText.length > 8,
                inputAccountExtras = null,
                inputPasswordExtras = inputPasswordExtras
            )
        }
        .stateIn(ioScope, SharingStarted.WhileSubscribed(), LoginAccountPasswordSideEffectState())

    fun updateInputAccount(value: TextFieldValue) {

    }

    fun updateInputPassword(value: TextFieldValue) {

    }

    fun doLogin() {

    }

}
