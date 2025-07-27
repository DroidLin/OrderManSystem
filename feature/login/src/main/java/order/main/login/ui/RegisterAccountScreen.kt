package order.main.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.login.ui.route.RegisterAccountRoute

@Composable
internal fun RegisterAccountScreen(
    modifier: Modifier = Modifier
) {

}

fun NavGraphBuilder.registerAccountScreen() {
    composable<RegisterAccountRoute> {

    }
}

fun NavController.navigateToRegisterAccount(inputAccount: String? = null) {
    navigate(RegisterAccountRoute(inputAccount)) {
        popUpTo<RegisterAccountRoute> {
            saveState = false
            inclusive = false
        }
        launchSingleTop = true
        restoreState = false
    }
}