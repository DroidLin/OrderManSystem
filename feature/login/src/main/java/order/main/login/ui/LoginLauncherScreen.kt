@file:Suppress("FunctionName")

package order.main.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.login.R

@Composable
internal fun LoginLauncherScreen(
    navigateToLoginAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(120.dp),
            imageVector = Icons.Default.Policy,
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(2f))
        Button(
            modifier = Modifier.padding(all = 16.dp),
            onClick = navigateToLoginAccount
        ) {
            Text(text = stringResource(R.string.login_string_login_with_phone))
        }
    }
}

fun NavGraphBuilder.loginLauncherScreen(
    navigateToLoginAccount: () -> Unit,
) {
    composable<LoginLauncherRoute> {
        LoginLauncherScreen(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            navigateToLoginAccount = navigateToLoginAccount
        )
    }
}
