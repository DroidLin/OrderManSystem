package order.main.foundation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun OnComposableLifecycle(
    lifecycleOwner: LifecycleOwner,
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onDestroy: () -> Unit = {}
) {
    val create by rememberUpdatedState(onCreate)
    val start by rememberUpdatedState(onStart)
    val resume by rememberUpdatedState(onResume)
    val pause by rememberUpdatedState(onPause)
    val stop by rememberUpdatedState(onStop)
    val destroy by rememberUpdatedState(onDestroy)
    DisposableEffect(true) {
        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> create()
                    Lifecycle.Event.ON_START -> start()
                    Lifecycle.Event.ON_RESUME -> resume()
                    Lifecycle.Event.ON_PAUSE -> pause()
                    Lifecycle.Event.ON_STOP -> stop()
                    Lifecycle.Event.ON_DESTROY -> destroy()
                    else -> {}
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    source.lifecycle.removeObserver(this)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            observer.onStateChanged(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}