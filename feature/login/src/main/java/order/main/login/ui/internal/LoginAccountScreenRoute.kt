package order.main.login.ui.internal

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
internal data class LoginAccountScreenRoute(
    val inputAccount: String?
)
