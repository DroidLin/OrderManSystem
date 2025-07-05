package order.main.foundation.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalActivity = compositionLocalOf<ComponentActivity> { error("not provided") }

val activity: ComponentActivity
    @Composable
    get() = LocalActivity.current

@Composable
fun ActivityProvider(activity: ComponentActivity, content: @Composable () -> Unit) {
    CompositionLocalProvider(value = LocalActivity provides activity, content = content)
}