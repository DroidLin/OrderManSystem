@file:Suppress("UndeclaredKoinUsage", "REDUNDANT_ELSE_IN_WHEN")

package order.main.system.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import order.main.foundation.KoinFoundation
import order.main.foundation.ui.asState
import order.main.foundation.ui.theme.FoundationTheme
import order.main.system.R
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

@Composable
fun LauncherComponent() {
    KoinContext(
        context = KoinFoundation.koin
    ) {
        val launcherViewModel = koinViewModel<AppLauncherViewModel>()
        val loadingAccount = launcherViewModel.userAccount.asState()

        FoundationTheme(
            modifier = Modifier.fillMaxSize()
        ) {
            when (val loadingState = loadingAccount.value) {
                is LauncherUiState.Loading -> LoadingScreen(modifier = Modifier)
                is LauncherUiState.Error -> LauncherFailureScreen(
                    modifier = Modifier.fillMaxSize(),
                    showMessage = loadingState.throwable.message
                )

                is LauncherUiState.Success -> App(
                    userAccount = loadingState.data,
                    modifier = Modifier
                )

                else -> LauncherFailureScreen(modifier = Modifier.fillMaxSize(), showMessage = null)
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(R.string.string_loading),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LauncherFailureScreen(
    showMessage: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(80.dp),
                imageVector = Icons.Outlined.Error,
                contentDescription = null
            )
            Text(
                modifier = Modifier,
                text = showMessage ?: stringResource(R.string.string_unknown_error),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}