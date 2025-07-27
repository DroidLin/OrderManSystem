@file:Suppress("FunctionName", "UndeclaredKoinUsage")

package order.main.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import order.main.foundation.ui.AppIconButton
import order.main.foundation.ui.FoundationPasswordTextField
import order.main.foundation.ui.FoundationTextField
import order.main.foundation.ui.appEventInstance
import order.main.foundation.ui.asState
import order.main.foundation.ui.koinViewModel
import order.main.foundation.ui.rememberDerivedStateOf
import order.main.foundation.ui.showSystemToast
import order.main.login.R
import order.main.login.internal.ui.data.InputExtras
import order.main.login.internal.ui.data.LoginAccountPasswordSideEffectState
import order.main.login.internal.ui.data.LoginAccountPasswordUiState
import order.main.login.internal.ui.vm.LoginAccountViewModel
import order.main.login.ui.route.LoginAccountScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
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
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {},
            navigationIcon = {
                AppIconButton(
                    onClick = backPress,
                    icon = Icons.Default.ArrowBackIosNew,
                    contentDescription = "back button"
                )
            },
            windowInsets = WindowInsets(0, 0, 0, 0)
        )
        Icon(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(120.dp),
            imageVector = Icons.Default.Policy,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.login_string_login_to_continue)
        )
        AppTextField(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth(0.75f),
            value = { uiStateGetter().inputAccount },
            inputExtras = { sideEffectStateGetter().inputAccountExtras },
            onValueChanged = updateInputAccount,
            label = stringResource(R.string.login_string_input_your_phone),
            singleLine = true,
            maxLines = 1,
            requestFocus = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        AppPasswordTextField(
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth(0.75f)
                .focusRequester(remember { FocusRequester() }),
            value = { uiStateGetter().inputPassword },
            inputExtras = { sideEffectStateGetter().inputPasswordExtras },
            onValueChanged = updateInputPassword,
            label = stringResource(R.string.login_string_input_your_password),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { doLogin() }
            ),
        )
        DoLoginButton(
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(max = 360.dp)
                .fillMaxWidth(0.5f),
            onClick = doLogin,
            isLoading = { uiStateGetter().isLoading },
            buttonEnable = { sideEffectStateGetter().doLoginButtonEnabled }
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DoLoginButton(
    onClick: () -> Unit,
    isLoading: () -> Boolean,
    buttonEnable: () -> Boolean,
    modifier: Modifier = Modifier
) {
    val enable by rememberDerivedStateOf(buttonEnable)
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable
    ) {
        val loading by rememberDerivedStateOf(isLoading)
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round
            )
        }
        Text(text = stringResource(R.string.login_string_login_button))
    }
}

@Composable
private fun AppTextField(
    value: () -> TextFieldValue,
    inputExtras: () -> InputExtras?,
    onValueChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    requestFocus: Boolean = false,
) {
    val inputValue by rememberDerivedStateOf(value)
    val extra by rememberDerivedStateOf(inputExtras)
    FoundationTextField(
        value = inputValue,
        onValueChange = onValueChanged,
        label = (extra?.message ?: label).let { message ->
            {
                Text(text = message)
            }
        },
        isError = extra?.isError ?: false,
        modifier = modifier.run {
            if (requestFocus) {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(focusRequester) {
                    delay(600L)
                    focusRequester.requestFocus()
                }
                modifier.focusRequester(focusRequester)
            } else this
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.medium,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
    )
}

@Composable
private fun AppPasswordTextField(
    value: () -> TextFieldValue,
    inputExtras: () -> InputExtras?,
    onValueChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    requestFocus: Boolean = false,
) {
    val inputValue by rememberDerivedStateOf(value)
    val extra by rememberDerivedStateOf(inputExtras)
    FoundationPasswordTextField(
        value = inputValue,
        onValueChange = onValueChanged,
        label = {
            val labelMessage by rememberDerivedStateOf { extra?.message ?: label }
            if (labelMessage.isNotEmpty()) {
                Text(text = labelMessage)
            }
        },
        isError = extra?.isError ?: false,
        modifier = modifier.run {
            if (requestFocus) {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(focusRequester) {
                    delay(600L)
                    focusRequester.requestFocus()
                }
                modifier.focusRequester(focusRequester)
            } else this
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.medium,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
    )
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
        val appEventInstance = appEventInstance
        LoginAccountScreen(
            modifier = LoginStaticModifier.LoginScreen.ContainerModifier,
            backPress = backPress,
            uiStateGetter = uiState::value,
            sideEffectStateGetter = sideEffectState::value,
            updateInputAccount = viewModel::updateInputAccount,
            updateInputPassword = viewModel::updateInputPassword,
            doLogin = {
                viewModel.doLogin(
                    showNotification = { message ->
                        appEventInstance.showSystemToast(message = message)
                    }
                )
            }
        )
    }
}
