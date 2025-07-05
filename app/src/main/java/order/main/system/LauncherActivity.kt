package order.main.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import order.main.foundation.ui.ActivityProvider
import order.main.foundation.ui.enableEdgeToEdge

class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityProvider(this) {
                App()
            }
        }
    }
}