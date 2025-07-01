package order.main.foundation.ui

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat


val Window.isLightSystemBar: Boolean
    get() = this.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

fun ComponentActivity.enableEdgeToEdge() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    this.window.decorView.systemUiVisibility = this.window.decorView.systemUiVisibility or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun Window.setStatusBarStyle(light: Boolean = false) {
    val systemUiVisibility = this.decorView.systemUiVisibility
    if (light) {
        this.decorView.systemUiVisibility =
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        this.decorView.systemUiVisibility =
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

fun Window.setNavigationBarStyle(light: Boolean = false) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        return
    }
    val systemUiVisibility = this.decorView.systemUiVisibility
    if (light) {
        this.decorView.systemUiVisibility =
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    } else {
        this.decorView.systemUiVisibility =
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
    }
}
