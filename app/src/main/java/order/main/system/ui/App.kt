@file:Suppress("UndeclaredKoinUsage")

package order.main.system.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import order.main.foundation.ui.LayerEventSurface
import order.main.user.GlobalAppStateProvider
import order.main.user.data.UserAccount
import order.main.user.globalAppState
import order.main.user.rememberGlobalAppState

@Composable
fun App(
    userAccount: UserAccount,
    modifier: Modifier = Modifier
) {
    GlobalAppStateProvider(globalAppState = rememberGlobalAppState(userAccount = userAccount)) {
        LayerEventSurface(modifier = modifier) {
            AppNavHost(navController = globalAppState.navController)
        }
    }
}
