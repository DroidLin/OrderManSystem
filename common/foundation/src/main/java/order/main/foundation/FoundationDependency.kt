package order.main.foundation

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Property
import org.koin.core.annotation.Singleton


@Module
class FoundationDependency {

    @AndroidContext
    @Factory(binds = [Context::class])
    fun applicationContext(@Property(KEY_APPLICATION_CONTEXT) context: Context): Context = context

    @Factory
    fun androidContext(@AndroidContext context: Context): Context = context

    @Singleton
    @AppLifecycleCoroutineScope
    fun appLifecycleCoroutineScope(): CoroutineScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Factory
    fun coroutineScope(
        @AppLifecycleCoroutineScope
        coroutineScope: CoroutineScope
    ): CoroutineScope = coroutineScope

    companion object {

        const val KEY_APPLICATION_CONTEXT = "key_application_context"
    }
}