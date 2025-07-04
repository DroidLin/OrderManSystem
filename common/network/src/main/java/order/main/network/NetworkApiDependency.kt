@file:OptIn(ExperimentalSerializationApi::class)

package order.main.network

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.protobuf.protobuf
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import order.main.foundation.AndroidContext
import order.main.foundation.AppLifecycleCoroutineScope
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Module
class NetworkApiDependency {

    @Single
    internal fun cookieDataStore(
        @AndroidContext
        context: Context,
        @AppLifecycleCoroutineScope
        coroutineScope: CoroutineScope,
    ): CookieDataStore = CookieDataStore(context, coroutineScope)

    @Single
    internal fun cookieStorage(
        cookieDataStore: CookieDataStore,
    ): AppCookieStorage = AppCookieStorage(cookieDataStore)

    @Singleton(binds = [HttpClient::class])
    internal fun httpClient(
        cookieStorage: AppCookieStorage,
    ): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
            protobuf()
        }
        install(HttpCookies) {
            storage = cookieStorage
        }
        install(HttpTimeout) {
            requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
            connectTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
            socketTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
        }
        defaultRequest {

        }
    }
}