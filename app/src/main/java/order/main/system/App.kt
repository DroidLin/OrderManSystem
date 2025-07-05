package order.main.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import order.main.foundation.ui.Event
import order.main.foundation.ui.LayerEventSurface
import order.main.foundation.ui.appEventInstance
import order.main.system.theme.FoundationTheme

@Composable
fun App() {
    FoundationTheme {
        LayerEventSurface(
            modifier = Modifier,
        ) {
            val coroutineScope = rememberCoroutineScope()
            val appEventInstance = appEventInstance
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        appEventInstance.tryPostEvent(
                            event = Event.Toast(
                                showMessage = "Hello World",
                                actionLabel = "你好",
                                onFinished = { ret ->
                                    println("toast result: $ret")
                                }
                            )
                        )
                    }
                ) {
                    Text(text = "Send Toast")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            appEventInstance.postEvent(
                                event = Event.SimpleNotification(
                                    title = "Hello World",
                                    subTitle = "你好 + ${System.currentTimeMillis()}",
                                    onFinished = { ret ->
                                        println("notification result: $ret")
                                    }
                                )
                            )
                        }
                    }
                ) {
                    Text(text = "Send Notification")
                }
            }
        }
    }
}