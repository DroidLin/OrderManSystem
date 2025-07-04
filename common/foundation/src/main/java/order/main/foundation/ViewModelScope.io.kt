package order.main.foundation


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private val VIEW_MODEL_SCOPE_LOCK = Any()

public val ViewModel.ioScope: CoroutineScope
    get() = synchronized(VIEW_MODEL_SCOPE_LOCK) {
        getCloseable(VIEW_MODEL_SCOPE_KEY)
            ?: createViewModelScope().also { scope ->
                addCloseable(VIEW_MODEL_SCOPE_KEY, scope)
            }
    }

public fun ViewModel.runOnIO(action: suspend () -> Unit): Job =
    this.runOnIO(context = EmptyCoroutineContext, start = CoroutineStart.DEFAULT, action = action)

public fun ViewModel.runOnIO(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    action: suspend () -> Unit
): Job = this.ioScope.launch(context = context, start = start) { action() }

/**
 * Key used to [ViewModel.addCloseable] the [CoroutineScope] associated with a [ViewModel].
 *
 * @see androidx.lifecycle.viewmodel.internal.ViewModelImpl
 * @see androidx.lifecycle.viewModelScope
 */
private const val VIEW_MODEL_SCOPE_KEY =
    "androidx.lifecycle.viewmodel.internal.ViewModelIOCoroutineScope.JOB_KEY"

/**
 * Creates a [CloseableCoroutineScope] intended for [ViewModel] use.
 *
 * The [CoroutineScope.coroutineContext] is configured with:
 * - [SupervisorJob]: ensures children jobs can fail independently of each other.
 * - [MainCoroutineDispatcher.immediate]: executes jobs immediately on the main (UI) thread. If
 *  the [Dispatchers.Main] is not available on the current platform (e.g., Linux), we fallback to
 *  an [EmptyCoroutineContext].
 *
 * For background execution, use [kotlinx.coroutines.withContext] to switch to appropriate
 * dispatchers (e.g., [kotlinx.coroutines.IO]).
 */
private fun createViewModelScope(): CloseableCoroutineScope {
    val dispatcher = try {
        // In platforms where `Dispatchers.Main` is not available, Kotlin Multiplatform will throw
        // an exception (the specific exception type may depend on the platform). Since there's no
        // direct functional alternative, we use `EmptyCoroutineContext` to ensure that a coroutine
        // launched within this scope will run in the same context as the caller.
        Dispatchers.IO
    } catch (_: NotImplementedError) {
        // In Native environments where `Dispatchers.Main` might not exist (e.g., Linux):
        EmptyCoroutineContext
    } catch (_: IllegalStateException) {
        // In JVM Desktop environments where `Dispatchers.Main` might not exist (e.g., Swing):
        EmptyCoroutineContext
    }
    return CloseableCoroutineScope(coroutineContext = dispatcher + SupervisorJob())
}

/** Represents this [CoroutineScope] as a [AutoCloseable]. */
private fun CoroutineScope.asCloseable() = CloseableCoroutineScope(coroutineScope = this)

/**
 * [CoroutineScope] that provides a method to [close] it, causing the rejection of any new tasks and
 * cleanup of all underlying resources associated with the scope.
 */
private class CloseableCoroutineScope(
    override val coroutineContext: CoroutineContext,
) : AutoCloseable, CoroutineScope {

    constructor(coroutineScope: CoroutineScope) : this(coroutineScope.coroutineContext)

    override fun close() = coroutineContext.cancel()
}
