package order.main.foundation

import android.content.Context
import order.main.foundation.KoinFoundation._koinApplication
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module

/**
 * app的依赖注入组件容器，所有的依赖注入相关管理都在[_koinApplication]中
 */
object KoinFoundation {

    private var _koinApplication: KoinApplication? = null

    val koin: Koin
        get() = requireNotNull(value = _koinApplication?.koin) { "_koinApplication is null, have you called KoinFoundation#initApplication?" }

    fun initApplication(context: Context, vararg modules: Module) {
        this._koinApplication?.close()
        this._koinApplication = KoinApplication.init()
            .properties(
                values = mapOf<String, Any>(
                    FoundationDependency.KEY_APPLICATION_CONTEXT to context,
                )
            )
            .modules(*modules)
    }
}