package order.main.foundation

import android.content.Context
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Property


@Module
class AppFoundationModule {

    @ApplicationContext
    @Factory(binds = [Context::class])
    fun applicationContext(@Property(KEY_APPLICATION_CONTEXT) context: Context): Context = context

    companion object {

        const val KEY_APPLICATION_CONTEXT = "key_application_context"
    }
}