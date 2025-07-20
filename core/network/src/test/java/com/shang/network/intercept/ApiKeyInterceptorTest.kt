package com.shang.network.intercept

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

/**
 * ApiKeyInterceptor 單元測試
 *
 * 測試攔截器是否正確為請求加上 api_key 查詢參數
 */
@DisplayName("ApiKeyInterceptor Tests")
class ApiKeyInterceptorTest {

    private val apiKey = "test_api_key"
    private val interceptor = ApiKeyInterceptor(apiKey)

    @Nested
    @DisplayName("intercept 方法測試")
    inner class InterceptMethod {
        @Test
        @DisplayName("應正確將 api_key 加入查詢參數")
        fun `should add api_key query param to request`() {
            // Given
            val originalUrl = HttpUrl.Builder()
                .scheme("https")
                .host("api.example.com")
                .addPathSegment("movie")
                .build()
            val originalRequest = Request.Builder()
                .url(originalUrl)
                .build()
            val chain = mockk<Interceptor.Chain>()
            val response = mockk<Response>()
            every { chain.request() } returns originalRequest
            every { chain.proceed(any()) } returns response

            // When
            val result = interceptor.intercept(chain)

            // Then
            verify { chain.proceed(any()) }
            // result 是 Response，無需判斷 null，直接驗證攔截後 request
            val capturedRequest = io.mockk.slot<Request>()
            verify { chain.proceed(capture(capturedRequest)) }
            val url = capturedRequest.captured.url
            expectThat(url.queryParameter(ApiKeyInterceptor.API_KEY_PARAM)).isEqualTo(apiKey)
            expectThat(url.queryParameterNames.contains(ApiKeyInterceptor.API_KEY_PARAM)).isTrue()
        }
    }
}
