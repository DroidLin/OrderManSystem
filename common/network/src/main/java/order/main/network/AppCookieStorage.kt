package order.main.network

import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url

internal class AppCookieStorage(private val cookieDataStore: CookieDataStore) : CookiesStorage {

    private val _cookieCache = mutableListOf<Cookie>()

    override suspend fun get(requestUrl: Url): List<Cookie> {
        return emptyList()
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
    }

    override fun close() {
    }
}