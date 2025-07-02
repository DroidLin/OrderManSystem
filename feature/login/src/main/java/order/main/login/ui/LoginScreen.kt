@file:Suppress("FunctionName")

package order.main.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.login.ui.internal.LoginScreenModel

@Composable
internal fun LoginScreen(
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {

}

fun NavGraphBuilder.LoginScreen(backPress: () -> Unit) {
    composable<LoginScreenModel> {
        LoginScreen(
            backPress = backPress,
            modifier = Modifier
        )
    }
}