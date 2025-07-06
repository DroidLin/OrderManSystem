package order.main.system.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import order.main.login.ui.LoginNavigationRoute
import order.main.login.ui.loginNavigation
import order.main.login.ui.navigateToLoginAccount
import order.main.user.globalAppState
import order.main.user.isLogin

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (globalAppState.userAccount.isLogin) {
            LoginNavigationRoute
        } else {
            LoginNavigationRoute
        },
        enterTransition = { fadeIn(tween(delayMillis = 300)) },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn(tween(delayMillis = 300)) },
        popExitTransition = { fadeOut() },
    ) {
        loginNavigation(
            backPress = navController::navigateUp,
            navigateToLoginAccount = navController::navigateToLoginAccount
        )
    }
}
