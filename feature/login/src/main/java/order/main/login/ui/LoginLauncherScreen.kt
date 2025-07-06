@file:Suppress("FunctionName")

package order.main.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

@Composable
internal fun LoginLauncherScreen(
    modifier: Modifier = Modifier
) {

}

fun NavGraphBuilder.loginLauncherScreen(
    navigateToLoginAccount: () -> Unit,
) {
    composable<LoginLauncherRoute> {
        LoginLauncherScreen(
            modifier = Modifier,
        )
    }
}
