package order.main.foundation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

private val LocalAppEventInstance = compositionLocalOf<AppEventInstance> { error("not provided") }

val appEventInstance: AppEventInstance
    @Composable
    get() = LocalAppEventInstance.current

interface Event {

    data class Toast(val showMessage: String) : Event
}

@Composable
fun EventConsumer(flow: Flow<Event>, consumer: suspend (Event) -> Unit) {
    LaunchedEffect(flow) {
        flow
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

    private val _eventChannel = Channel<Event>()

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