package order.main.foundation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

private val LocalAppEventInstance = compositionLocalOf<AppEventInstance> { error("not provided") }

val appEventInstance: AppEventInstance
    @Composable
    get() = LocalAppEventInstance.current

enum class ToastActionResult {
    Dismissed,
    ActionPerformed,
}

interface Event {

    val onFinished: (ToastActionResult) -> Unit

    data class Toast(
        val showMessage: String,
        val actionLabel: String? = null,
        override val onFinished: (ToastActionResult) -> Unit = {}
    ) : Event

    sealed class Notification : Event

    data class SimpleNotification(
        val title: String,
        val subTitle: String? = null,
        val icon: String? = null,
        override val onFinished: (ToastActionResult) -> Unit
    ) : Notification()

    data class RichNotification(
        val title: AnnotatedString,
        val subTitle: AnnotatedString? = null,
        val icon: String? = null,
        override val onFinished: (ToastActionResult) -> Unit
    ) : Notification()

}

@Composable
fun EventConsumer(flow: Flow<Event>, consumer: suspend (Event) -> Unit) {
    LaunchedEffect(flow) {
        flow
            .cancellable()
            .onEach(action = consumer)
            .collect()
    }
}

@Composable
fun rememberAppEventInstance(): AppEventInstance {
    return remember { AppEventInstance() }
}

@Composable
fun AppEventProvider(content: @Composable () -> Unit) {
    val appEventInstance = rememberAppEventInstance()
    CompositionLocalProvider(value = LocalAppEventInstance provides appEventInstance, content = content)
}

class AppEventInstance() {

    private val _eventChannel = Channel<Event>(capacity = 5)

    val eventFlow = this._eventChannel.receiveAsFlow()

    suspend fun postEvent(event: Event) {
        this._eventChannel.send(event)
    }

    fun tryPostEvent(event: Event): Boolean {
        val trySendResult = this._eventChannel.trySend(event)
        if (trySendResult.isFailure) {
            trySendResult.exceptionOrNull()?.printStackTrace()
        }
        return trySendResult.isSuccess
    }
}

@Composable
fun LayerEventSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val notificationHostState = remember { NotificationHostState() }
    val snackBarHostState = remember { SnackbarHostState() }
    AppEventProvider {
        LayerEventController(
            notificationHostState = notificationHostState,
            snackBarHostState = snackBarHostState
        )
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            // render content
            content()

            // notifications
            LayerNotificationAreas(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                    .align(Alignment.TopCenter),
                notificationHostState = notificationHostState,
            )

            // bottom toast
            LayerSnackBarAreas(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter),
                snackBarHostState = snackBarHostState,
            )
        }
    }
}

/**
 * 新增度通知类型在此补充
 */
@Composable
private fun LayerEventController(
    notificationHostState: NotificationHostState,
    snackBarHostState: SnackbarHostState
) {
    EventConsumer(appEventInstance.eventFlow) { event ->
        var finishResult = ToastActionResult.Dismissed
        when (event) {
            is Event.Toast -> {
                val ret = snackBarHostState.showSnackbar(
                    message = event.showMessage,
                    actionLabel = event.actionLabel,
                    withDismissAction = event.actionLabel.isNullOrEmpty()
                )
                finishResult = when (ret) {
                    SnackbarResult.ActionPerformed -> ToastActionResult.ActionPerformed
                    else -> ToastActionResult.Dismissed
                }
            }

            is Event.SimpleNotification -> {
                val ret = notificationHostState.postNotification(
                    notification = AppNotification.Simple(
                        title = event.title,
                        subTitle = event.subTitle,
                        icon = event.icon
                    )
                )
                finishResult = when (ret) {
                    AppNotificationResult.ActionPerformed -> ToastActionResult.ActionPerformed
                    else -> ToastActionResult.Dismissed
                }
            }
        }
        event.onFinished(finishResult)
    }
}

@Composable
private fun LayerNotificationAreas(
    notificationHostState: NotificationHostState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        NotificationHost(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .heightIn(min = 80.dp)
                .fillMaxWidth(),
            notificationHostState = notificationHostState,
        )
    }
}

@Composable
private fun LayerSnackBarAreas(
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        SnackbarHost(
            modifier = Modifier,
            hostState = snackBarHostState,
            snackbar = { data ->
                Snackbar(
                    snackbarData = data,
                    shape = MaterialTheme.shapes.large,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    actionColor = MaterialTheme.colorScheme.primary,
                    actionContentColor = MaterialTheme.colorScheme.primary,
                    dismissActionContentColor = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}
