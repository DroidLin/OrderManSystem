@file:Suppress("FunctionName")

package order.main.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.login.ui.internal.LoginAccountScreenModel

@Composable
internal fun LoginAccountScreen(
    backPress: () -> Unit,
    modifier: Modifier = Modifier
) {

}

fun NavGraphBuilder.LoginAccountScreen(backPress: () -> Unit) {
    composable<LoginAccountScreenModel> {
        LoginAccountScreen(
            backPress = backPress,
            modifier = Modifier
        )
    }
}