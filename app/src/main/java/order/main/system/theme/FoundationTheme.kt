package order.main.system.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

val Configuration.isDarkTheme: Boolean
    get() = (this.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

@Composable
fun FoundationTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val isDarkTheme = LocalConfiguration.current.isDarkTheme
    val colorScheme = remember(isDarkTheme, context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (isDarkTheme) {
                dynamicDarkColorScheme(context = context)
            } else {
                dynamicLightColorScheme(context = context)
            }
        } else {
            if (isDarkTheme) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }
        }
    }
    MaterialTheme(colorScheme = colorScheme) {
        Surface(content = content)
    }
}