package order.main.login.internal.ui.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue

@Stable
internal data class LoginAccountPasswordUiState(
    val isLoading: Boolean = false,
    val inputAccount: TextFieldValue = TextFieldValue(),
    val inputPassword: TextFieldValue = TextFieldValue(),
)