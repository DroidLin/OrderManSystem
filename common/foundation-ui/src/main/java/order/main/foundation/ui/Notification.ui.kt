package order.main.foundation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

class NotificationMetadata(
    val notification: AppNotification,
    val duration: AppNotificationDuration,
    private val continuation: CancellableContinuation<AppNotificationResult>
) {

    fun performAction() {
        if (this.continuation.isActive) {
            this.continuation.resume(AppNotificationResult.ActionPerformed)
        }
    }

    fun dismiss() {
        if (this.continuation.isActive) {
            this.continuation.resume(AppNotificationResult.Dismissed)
        }
    }
}

class AppNotificationHostState {

    private val _mutex = Mutex()

    var currentNotification by mutableStateOf<NotificationMetadata?>(null)
        private set

    suspend fun postNotification(
        notification: AppNotification,
        duration: AppNotificationDuration = AppNotificationDuration.Short
    ): AppNotificationResult {
        return this._mutex.withLock {
            try {
                suspendCancellableCoroutine { continuation ->
                    this.currentNotification =
                        NotificationMetadata(notification, duration, continuation)
                }
            } finally {
                this.currentNotification = null
            }
        }
    }
}

enum class AppNotificationDuration {
    Short,
    Long,
}

enum class AppNotificationResult {
    Dismissed,
    ActionPerformed
}

internal fun AppNotificationDuration.toMilliSeconds(): Long {
    return when (this) {
        AppNotificationDuration.Long -> 10_000L
        else -> 5_000L
    }
}

@Stable
sealed class AppNotification {

    data class Simple(
        val title: String,
        val subTitle: String? = null,
        val icon: String? = null
    ) : AppNotification()

    data class Rich(
        val title: AnnotatedString,
        val subTitle: AnnotatedString? = null,
        val icon: @Composable () -> Unit,
    ) : AppNotification()
}

private const val DURATION = 600

private val EnterTransition =
    slideInVertically(animationSpec = tween(DURATION)) { -it } +
            fadeIn(animationSpec = tween(DURATION))
private val ExitTransition =
    slideOutVertically(animationSpec = tween(DURATION)) { -it } +
            fadeOut(animationSpec = tween(DURATION))

private val PopEnterTransition =
    slideInVertically(animationSpec = tween(DURATION, DURATION)) { -it } +
            fadeIn(animationSpec = tween(DURATION, DURATION))

@Composable
fun NotificationHost(
    appNotificationHostState: AppNotificationHostState,
    modifier: Modifier = Modifier,
) {
    val currentNotificationMetadata = appNotificationHostState.currentNotification
    val visibleState = remember { MutableTransitionState(false) }
    visibleState.targetState = currentNotificationMetadata != null
    LaunchedEffect(currentNotificationMetadata) {
        if (currentNotificationMetadata != null) {
            val duration = currentNotificationMetadata.duration.toMilliSeconds()
            delay(duration)
            currentNotificationMetadata.dismiss()
        }
    }
    AnimatedVisibility(
        modifier = modifier,
        visibleState = visibleState,
        enter = EnterTransition,
        exit = ExitTransition
    ) {
        var notification by remember { mutableStateOf<NotificationMetadata?>(null) }
        if (currentNotificationMetadata != null) {
            notification = currentNotificationMetadata
        }
        AnimatedContent(
            targetState = notification,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                if (this.initialState == null) {
                    EnterTransition togetherWith ExitTransition
                } else {
                    PopEnterTransition togetherWith ExitTransition
                }
            },
            label = "app_build_in_notification_animation_content"
        ) { notification ->
            if (notification == null) {
                Box(modifier = Modifier.fillMaxWidth())
                return@AnimatedContent
            }
            NotificationContent(
                modifier = Modifier
                    .padding(all = 16.dp),
                notification = notification.notification,
                onPress = notification::performAction
            )
        }
    }
}

@Composable
fun NotificationContent(
    notification: AppNotification,
    onPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        onClick = onPress,
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shadowElevation = 5.dp,
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        when (notification) {
            is AppNotification.Simple -> SimpleNotificationContent(
                simpleNotification = notification,
                modifier = Modifier.padding(all = 16.dp)
            )

            else -> {}
        }
    }
}

@Composable
fun SimpleNotificationContent(
    simpleNotification: AppNotification.Simple,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = simpleNotification.title,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val subTitle = simpleNotification.subTitle
            if (!subTitle.isNullOrEmpty()) {
                Text(
                    modifier = Modifier,
                    text = subTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}