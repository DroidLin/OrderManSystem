package order.main.login.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import order.main.login.ui.route.LoginLauncherRoute
import order.main.login.ui.route.LoginNavigationRoute

fun NavGraphBuilder.loginNavigation(
    backPress: () -> Unit,
    navigateToLoginAccount: () -> Unit,
) {
    navigation<LoginNavigationRoute>(startDestination = LoginLauncherRoute) {
        loginLauncherScreen(navigateToLoginAccount = navigateToLoginAccount)
        loginAccountScreen(backPress = backPress)
    }
}