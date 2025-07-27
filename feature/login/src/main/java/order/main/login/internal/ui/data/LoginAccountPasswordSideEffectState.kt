package order.main.login.internal.ui.data

import androidx.compose.runtime.Stable

@Stable
data class LoginAccountPasswordSideEffectState(
    val inputAccountExtras: InputExtras? = null,
    val inputPasswordExtras: InputExtras? = null,
    val doLoginButtonEnabled: Boolean = false
)

data class InputExtras(
    val isError: Boolean = false,
    val message: String? = null
)