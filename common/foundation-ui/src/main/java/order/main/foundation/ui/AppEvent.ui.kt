package order.main.foundation.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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

    sealed class Toast : Event {

        data class SystemToast(
            val showMessage: String,
            override val onFinished: (ToastActionResult) -> Unit = {},
        ) : Toast()

        data class SnackBarToast(
            val showMessage: String,
            val actionLabel: String? = null,
            override val onFinished: (ToastActionResult) -> Unit = {}
        ) : Toast()

    }

    sealed class Notification : Event {

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

class AppEventInstance {

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
    val topNotificationHostState = remember {
        NotificationHostState<Notification.TopNotification>()
    }
    val bottomToastNotificationHostState = remember {
        NotificationHostState<Notification.Toast>()
    }
    AppEventProvider {
        LayerEventController(
            topNotificationHostState = topNotificationHostState,
            bottomToastNotificationHostState = bottomToastNotificationHostState
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
                notificationHostState = topNotificationHostState,
            )

            // bottom toast
            LayerSnackBarAreas(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter),
                notificationHostState = bottomToastNotificationHostState,
            )
        }
    }
}

/**
 * 新增度通知类型在此补充
 */
@Composable
private fun LayerEventController(
    topNotificationHostState: NotificationHostState<Notification.TopNotification>,
    bottomToastNotificationHostState: NotificationHostState<Notification.Toast>,
) {
    EventConsumer(appEventInstance.eventFlow) { event ->
        val finishResult = when (event) {
            is Event.Notification.SimpleNotification -> {
                topNotificationHostState.postNotification(
                    notification = Notification.TopNotification.Simple(
                        title = event.title,
                        subTitle = event.subTitle,
                        icon = event.icon
                    )
                )
            }

            is Event.Toast.SnackBarToast -> {
                bottomToastNotificationHostState.postNotification(
                    Notification.Toast.ToastSnackBarMessage(
                        showMessage = event.showMessage,
                        actionLabel = event.actionLabel
                    ),
                    if (event.actionLabel == null) {
                        NotificationDuration.Short
                    } else NotificationDuration.Long
                )
            }

            is Event.Toast.SystemToast -> {
                bottomToastNotificationHostState.postNotification(
                    notification = Notification.Toast.SystemToast(showMessage = event.showMessage)
                )
            }

            else -> NotificationResult.Dismissed
        }.let {
            when (it) {
                NotificationResult.ActionPerformed -> ToastActionResult.ActionPerformed
                else -> ToastActionResult.Dismissed
            }
        }
        event.onFinished(finishResult)
    }
}

private const val DURATION = 400

private val NotificationEnterTransition =
    slideInVertically(animationSpec = tween(DURATION)) { -it } +
            fadeIn(animationSpec = tween(DURATION))
private val NotificationExitTransition =
    slideOutVertically(animationSpec = tween(DURATION)) { -it } +
            fadeOut(animationSpec = tween(DURATION))
private val NotificationPopEnterTransition =
    slideInVertically(animationSpec = tween(DURATION, DURATION)) { -it } +
            fadeIn(animationSpec = tween(DURATION, DURATION))

@Composable
private fun LayerNotificationAreas(
    notificationHostState: NotificationHostState<Notification.TopNotification>,
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
            enterTransition = NotificationEnterTransition,
            exitTransition = NotificationExitTransition,
            popEnterTransition = NotificationPopEnterTransition,
            popExitTransition = NotificationExitTransition
        ) {
            DefaultNotification(it)
        }
    }
}

private val ToastEnterTransition =
    slideInVertically(animationSpec = tween(DURATION)) { it } +
            fadeIn(animationSpec = tween(DURATION))
private val ToastExitTransition =
    slideOutVertically(animationSpec = tween(DURATION)) { it } +
            fadeOut(animationSpec = tween(DURATION))
private val ToastPopEnterTransition =
    slideInVertically(animationSpec = tween(DURATION, DURATION)) { it } +
            fadeIn(animationSpec = tween(DURATION, DURATION))

@Composable
private fun LayerSnackBarAreas(
    notificationHostState: NotificationHostState<Notification.Toast>,
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
            enterTransition = ToastEnterTransition,
            exitTransition = ToastExitTransition,
            popEnterTransition = ToastPopEnterTransition,
            popExitTransition = ToastExitTransition
        ) {
            DefaultNotification(it)
        }
    }
}
