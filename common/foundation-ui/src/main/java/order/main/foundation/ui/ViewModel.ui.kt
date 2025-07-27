package order.main.foundation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.compose.currentKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.mp.KoinPlatformTools
import org.koin.viewmodel.defaultExtras
import org.koin.viewmodel.factory.KoinViewModelFactory
import org.koin.viewmodel.getViewModelKey
import kotlin.reflect.KClass

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> koinViewModel(
    qualifier: Qualifier? = null,
    viewModelStoreOwner: ViewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"),
    key: String? = null,
    extras: CreationExtras = defaultExtras(viewModelStoreOwner),
    scope: Scope = currentKoinScope(),
): T {
    val vmClass = remember { T::class }
    val viewModelStore = viewModelStoreOwner.viewModelStore

    return resolveViewModel(
        vmClass = vmClass,
        viewModelStore = viewModelStore,
        key = key,
        extras = extras,
        qualifier = qualifier,
        scope = scope
    )
}

@KoinInternalApi
@Composable
fun <T : ViewModel> resolveViewModel(
    vmClass: KClass<T>,
    viewModelStore: ViewModelStore,
    key: String? = null,
    extras: CreationExtras,
    qualifier: Qualifier? = null,
    scope: Scope,
): T {
    val factory = remember(vmClass, scope, qualifier) {
        KoinViewModelFactory(vmClass, scope, qualifier)
    }
    val provider = remember(viewModelStore, factory, extras) {
        ViewModelProvider.create(viewModelStore, factory, extras)
    }
    val className = remember(vmClass) {
        KoinPlatformTools.getClassFullNameOrNull(vmClass)
    }
    val vmKey = remember(qualifier, key, className) {
        getViewModelKey(qualifier, key, className)
    }
    return when {
        vmKey != null -> provider[vmKey, vmClass]
        else -> provider[vmClass]
    }
}
