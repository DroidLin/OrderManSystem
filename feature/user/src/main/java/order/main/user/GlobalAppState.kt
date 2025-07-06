package order.main.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import order.main.user.data.UserAccount

val LocalGlobalAppState = compositionLocalOf<GlobalAppState> { error("not provided") }

val globalAppState: GlobalAppState
    @Composable
    get() = LocalGlobalAppState.current

@Composable
fun GlobalAppStateProvider(globalAppState: GlobalAppState, content: @Composable () -> Unit) {
    CompositionLocalProvider(value = LocalGlobalAppState provides globalAppState, content = content)
}

@Composable
fun rememberGlobalAppState(
    userAccount: UserAccount,
    navController: NavHostController = rememberNavController()
): GlobalAppState {
    return remember(userAccount, navController) {
        GlobalAppState(
            userAccount = userAccount,
            navController = navController,
        )
    }
}

class GlobalAppState(
    val userAccount: UserAccount,
    val navController: NavHostController
) {
}