package order.main.login.ui.route

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class RegisterAccountRoute(
    val inputAccount: String? = null
)
