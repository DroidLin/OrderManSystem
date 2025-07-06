package order.main.foundation.ui

import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalActivity = compositionLocalOf<ComponentActivity> { error("not provided") }

val LocalWindow = compositionLocalOf<Window> { error("not provided") }

val activity: ComponentActivity
    @Composable
    get() = LocalActivity.current

val window: Window
    @Composable
    get() = LocalWindow.current

@Composable
fun ActivityProvider(activity: ComponentActivity, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        values = arrayOf(
            LocalActivity provides activity,
            LocalWindow provides activity.window
        ),
        content = content
    )
}