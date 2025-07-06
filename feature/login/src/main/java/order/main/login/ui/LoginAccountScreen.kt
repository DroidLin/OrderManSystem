@file:Suppress("FunctionName", "UndeclaredKoinUsage")

package order.main.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.foundation.ui.asState
import order.main.login.ui.internal.model.LoginAccountPasswordSideEffectState
import order.main.login.ui.internal.model.LoginAccountPasswordUiState
import order.main.login.ui.internal.vm.LoginAccountViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginAccountScreen(
    backPress: () -> Unit,
    uiStateGetter: () -> LoginAccountPasswordUiState,
    sideEffectStateGetter: () -> LoginAccountPasswordSideEffectState,
    updateInputAccount: (TextFieldValue) -> Unit,
    updateInputPassword: (TextFieldValue) -> Unit,
    doLogin: () -> Unit,
    modifier: Modifier = Modifier
) {

}

fun NavController.navigateToLoginAccount(inputAccount: String? = null) {
    navigate(LoginAccountScreenRoute(inputAccount)) {
        popUpTo<LoginAccountScreenRoute> {
            saveState = false
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginAccountScreen(backPress: () -> Unit) {
    composable<LoginAccountScreenRoute> {
        val viewModel = koinViewModel<LoginAccountViewModel>()
        val uiState = viewModel.uiState.asState()
        val sideEffectState = viewModel.sideEffectState.asState()
        LoginAccountScreen(
            backPress = backPress,
            uiStateGetter = uiState::value,
            sideEffectStateGetter = sideEffectState::value,
            updateInputAccount = viewModel::updateInputAccount,
            updateInputPassword = viewModel::updateInputPassword,
            doLogin = viewModel::doLogin,
            modifier = Modifier
        )
    }
}
