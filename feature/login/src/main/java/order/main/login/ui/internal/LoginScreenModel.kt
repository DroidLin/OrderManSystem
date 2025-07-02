package order.main.login.ui.internal

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class LoginScreenModel(
    val inputAccount: String?
)
