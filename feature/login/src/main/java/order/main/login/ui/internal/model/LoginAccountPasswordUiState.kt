package order.main.login.ui.internal.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue

@Stable
data class LoginAccountPasswordUiState(
    val inputAccount: TextFieldValue = TextFieldValue(),
    val inputPassword: TextFieldValue = TextFieldValue(),
)