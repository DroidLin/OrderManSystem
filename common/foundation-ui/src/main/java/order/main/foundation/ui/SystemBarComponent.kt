package order.main.foundation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun SystemBarComponent(isLightMode: Boolean) {
    val window = window
    DisposableEffect(window, isLightMode) {
        val isLightSystemBar = window.isLightSystemBar
        if (isLightSystemBar != isLightMode) {
            window.setStatusBarStyle(isLightMode)
            window.setNavigationBarStyle(isLightMode)
        }
        onDispose {
            window.setStatusBarStyle(isLightSystemBar)
            window.setNavigationBarStyle(isLightSystemBar)
        }
    }
}