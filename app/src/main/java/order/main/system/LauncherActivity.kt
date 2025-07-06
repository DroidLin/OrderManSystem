@file:Suppress("UndeclaredKoinUsage")

package order.main.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import order.main.foundation.ui.ActivityProvider
import order.main.foundation.ui.enableEdgeToEdge
import order.main.system.ui.LauncherComponent

class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContent {
            ActivityProvider(activity = this) {
                LauncherComponent()
            }
        }
    }
}