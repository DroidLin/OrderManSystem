@file:OptIn(ExperimentalSerializationApi::class)

package order.main.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.protobuf.protobuf
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class NetworkApiModule {

    @Singleton(binds = [HttpClient::class])
    fun httpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
            protobuf()
        }
        install(HttpCookies) {
            storage = ApplicationCookieContainer()
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