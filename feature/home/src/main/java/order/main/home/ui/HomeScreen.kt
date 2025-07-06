package order.main.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import order.main.home.R

@Composable
fun HomeScreen(
    doRealLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello World",
            style = MaterialTheme.typography.titleLarge
        )
        TextButton(onClick = doRealLogout) {
            Text(text = stringResource(R.string.home_string_do_logout))
        }
    }
}

fun NavGraphBuilder.homeScreen(
    doRealLogout: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            doRealLogout = doRealLogout,
            modifier = Modifier.fillMaxSize(),
        )
    }
}