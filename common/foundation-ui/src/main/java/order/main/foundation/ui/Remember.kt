package order.main.foundation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember


@Composable
fun <T> rememberDerivedStateOf(calculation: () -> T): State<T> {
    return remember { derivedStateOf(calculation) }
}

@Composable
fun <T> rememberDerivedStateOf(vararg key: Any, calculation: () -> T): State<T> {
    return remember(*key) { derivedStateOf(calculation) }
}
