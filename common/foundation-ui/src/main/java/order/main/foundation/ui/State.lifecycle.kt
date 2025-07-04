package order.main.foundation.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> StateFlow<T>.asState(): State<T> = this.asState(initValue = this.value)

@Composable
fun <T> Flow<T>.asState(initValue: T): State<T> =
    this.collectAsStateWithLifecycle(initialValue = initValue)