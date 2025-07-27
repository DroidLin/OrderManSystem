package order.main.system.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import order.main.home.ui.homeScreen
import order.main.login.ui.route.LoginNavigationRoute
import order.main.login.ui.loginNavigation
import order.main.login.ui.navigateToLoginAccount
import order.main.user.globalAppState
import order.main.user.isLogin

private const val duration = 500

private val enterTransition = fadeIn(tween(durationMillis = duration, delayMillis = duration))
private val exitTransition = fadeOut(tween(durationMillis = duration))

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
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { enterTransition },
        popExitTransition = { exitTransition },
    ) {
        loginNavigation(
            backPress = navController::navigateUp,
            navigateToLoginAccount = navController::navigateToLoginAccount
        )
        homeScreen(
            doRealLogout = {}
        )
    }
}
