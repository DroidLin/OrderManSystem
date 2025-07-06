package order.main.foundation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

@Stable
interface NotificationController {

    fun performAction()

    fun dismiss()
}

interface NotificationVisual<T> : NotificationController {

    val notification: T
}

class NotificationMetadata<T>(
    override val notification: T,
    val duration: NotificationDuration,
    private val continuation: CancellableContinuation<NotificationResult>
) : NotificationVisual<T> {

    override fun performAction() {
        if (this.continuation.isActive) {
            this.continuation.resume(NotificationResult.ActionPerformed)
        }
    }

    override fun dismiss() {
        if (this.continuation.isActive) {
            this.continuation.resume(NotificationResult.Dismissed)
        }
    }
}

@Stable
class NotificationHostState<T : Any> {

    private val _mutex = Mutex()

    private val _notificationMetadata = MutableStateFlow<NotificationMetadata<T>?>(null)
    val notificationMetadata = this._notificationMetadata.asStateFlow()

    suspend fun postNotification(
        notification: T,
        duration: NotificationDuration = NotificationDuration.Short
    ): NotificationResult {
        return this._mutex.withLock {
            try {
                suspendCancellableCoroutine { continuation ->
                    this._notificationMetadata.value =
                        NotificationMetadata(notification, duration, continuation)
                }
            } finally {
                this._notificationMetadata.value = null
            }
        }
    }
}

enum class NotificationDuration {
    Short,
    Long,
}

enum class NotificationResult {
    Dismissed,
    ActionPerformed
}

internal fun NotificationDuration.toMilliSeconds(): Long {
    return when (this) {
        NotificationDuration.Long -> 10_000L
        else -> 5_000L
    }
}

@Stable
sealed class Notification {

    sealed class Toast() : Notification() {
        data class SystemToast(
            val showMessage: String
        ) : Toast()

        data class ToastSnackBarMessage(
            val showMessage: String,
            val actionLabel: String? = null
        ) : Toast()
    }

    sealed class TopNotification() : Notification() {

        data class Simple(
            val title: String,
            val subTitle: String? = null,
            val icon: String? = null
        ) : TopNotification()

        data class Rich(
            val title: AnnotatedString,
            val subTitle: AnnotatedString? = null,
            val icon: @Composable () -> Unit,
        ) : TopNotification()
    }
}

@Composable
fun <T : Any> NotificationHost(
    notificationHostState: NotificationHostState<T>,
    modifier: Modifier = Modifier,
    enterTransition: EnterTransition = fadeIn(),
    exitTransition: ExitTransition = fadeOut(),
    popEnterTransition: EnterTransition = enterTransition,
    popExitTransition: ExitTransition = exitTransition,
    notification: @Composable AnimatedContentScope.(NotificationMetadata<T>) -> Unit = {
        DefaultNotification(it)
    },
) {
    val currentNotificationMetadataState = notificationHostState.notificationMetadata.asState()
    val visibleState = remember { MutableTransitionState(false) }
    val currentNotificationMetadata = currentNotificationMetadataState.value
    LaunchedEffect(currentNotificationMetadata) {
        visibleState.targetState = currentNotificationMetadata != null
        if (currentNotificationMetadata != null) {
            val duration = currentNotificationMetadata.duration.toMilliSeconds()
            delay(duration)
            currentNotificationMetadata.dismiss()
        }
    }
    AnimatedVisibility(
        modifier = modifier,
        visibleState = visibleState,
        enter = enterTransition,
        exit = exitTransition
    ) {
        val notificationState = remember { mutableStateOf<NotificationMetadata<T>?>(null) }
        if (currentNotificationMetadata != null && notificationState.value != currentNotificationMetadata) {
            notificationState.value = currentNotificationMetadata
        }
        val notification = notificationState.value
        if (notification == null) {
            return@AnimatedVisibility
        }
        AnimatedContent(
            modifier = Modifier,
            targetState = notification,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                popEnterTransition togetherWith popExitTransition
            },
            label = "app_build_in_notification_animation_content"
        ) { notification ->
            notification(notification)
        }
    }
}

@Composable
fun <T> AnimatedContentScope.DefaultNotification(
    metadata: NotificationMetadata<T>,
    modifier: Modifier = Modifier,
) {
    NotificationContent(
        modifier = modifier.padding(16.dp),
        metadata = metadata,
        notification = metadata.notification,
        isAnimRunning = this.transition::isRunning
    )
}

@Composable
fun <T> NotificationContent(
    metadata: NotificationMetadata<T>,
    notification: T,
    isAnimRunning: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (notification) {
            is Notification.TopNotification -> TopNotificationUi(
                data = notification,
                controller = metadata,
                isAnimRunning = isAnimRunning,
                modifier = Modifier.fillMaxWidth()
            )

            is Notification.Toast -> ToastUi(
                data = notification,
                controller = metadata,
                isAnimRunning = isAnimRunning,
                modifier = Modifier
            )

            else -> {}
        }
    }
}
