package com.shang.network.intercept

import com.shang.common.LanguageProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * LanguageInterceptor 單元測試
 *
 * 測試攔截器是否根據 LanguageProvider 的語言設定，正確為請求加上 language 查詢參數
 */
@DisplayName("LanguageInterceptor Tests")
class LanguageInterceptorTest {

    private lateinit var mockLanguageProvider: LanguageProvider
    private lateinit var mockChain: Interceptor.Chain
    private lateinit var mockRequest: Request
    private lateinit var mockResponse: Response
    private lateinit var languageInterceptor: LanguageInterceptor

    @BeforeEach
    fun setUp() {
        mockLanguageProvider = mockk()
        mockChain = mockk()
        mockRequest = mockk()
        mockResponse = mockk()
        languageInterceptor = LanguageInterceptor(mockLanguageProvider)
    }

    @Nested
    @DisplayName("語言參數添加測試")
    inner class LanguageParameterTests {

        @Test
        @DisplayName("當語言為英文時，應該添加 language=en 查詢參數")
        fun `when language is english, should add language=en query parameter`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "en"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            val result = languageInterceptor.intercept(mockChain)

            // Then
            expectThat(result).isEqualTo(mockResponse)
            verify { mockLanguageProvider.getLanguageCode() }
            verify {
                mockChain.proceed(
                    match { request ->
                        request.url.queryParameter("language") == expectedLanguage
                    },
                )
            }
        }

        @Test
        @DisplayName("當語言為中文時，應該添加 language=zh 查詢參數")
        fun `when language is chinese, should add language=zh query parameter`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "zh"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            val result = languageInterceptor.intercept(mockChain)

            // Then
            expectThat(result).isEqualTo(mockResponse)
            verify { mockLanguageProvider.getLanguageCode() }
            verify {
                mockChain.proceed(
                    match { request ->
                        request.url.queryParameter("language") == expectedLanguage
                    },
                )
            }
        }

        @Test
        @DisplayName("當 URL 已經有其他查詢參數時，應該正確添加 language 參數")
        fun `when url already has query parameters, should correctly add language parameter`() =
            runTest {
                // Given
                val originalUrl = "https://api.example.com/movies?page=1&limit=20"
                val expectedLanguage = "en"
                val httpUrl = originalUrl.toHttpUrl()

                every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
                every { mockChain.request() } returns mockRequest
                every { mockRequest.url } returns httpUrl
                every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
                every { mockChain.proceed(any()) } returns mockResponse

                // When
                val result = languageInterceptor.intercept(mockChain)

                // Then
                expectThat(result).isEqualTo(mockResponse)
                verify {
                    mockChain.proceed(
                        match { request ->
                            val url = request.url
                            url.queryParameter("language") == expectedLanguage &&
                                url.queryParameter("page") == "1" &&
                                url.queryParameter("limit") == "20"
                        },
                    )
                }
            }
    }

    @Nested
    @DisplayName("邊界情況測試")
    inner class EdgeCaseTests {

        @Test
        @DisplayName("當語言代碼為空字串時，應該添加空的 language 參數")
        fun `when language code is empty string, should add empty language parameter`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = ""
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            val result = languageInterceptor.intercept(mockChain)

            // Then
            expectThat(result).isEqualTo(mockResponse)
            verify {
                mockChain.proceed(
                    match { request ->
                        request.url.queryParameter("language") == expectedLanguage
                    },
                )
            }
        }

        @Test
        @DisplayName("當語言代碼包含特殊字符時，應該正確處理")
        fun `when language code contains special characters, should handle correctly`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "zh-TW"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            val result = languageInterceptor.intercept(mockChain)

            // Then
            expectThat(result).isEqualTo(mockResponse)
            verify {
                mockChain.proceed(
                    match { request ->
                        request.url.queryParameter("language") == expectedLanguage
                    },
                )
            }
        }
    }

    @Nested
    @DisplayName("攔截器行為測試")
    inner class InterceptorBehaviorTests {

        @Test
        @DisplayName("應該只呼叫一次 LanguageProvider.getLanguageCode()")
        fun `should call LanguageProvider getLanguageCode only once`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "en"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            languageInterceptor.intercept(mockChain)

            // Then
            verify(exactly = 1) { mockLanguageProvider.getLanguageCode() }
        }

        @Test
        @DisplayName("應該呼叫 Chain.proceed() 一次並傳遞修改後的請求")
        fun `should call chain proceed once with modified request`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "en"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            languageInterceptor.intercept(mockChain)

            // Then
            verify(exactly = 1) { mockChain.proceed(any()) }
            verify { mockChain.request() }
        }

        @Test
        @DisplayName("應該回傳 Chain.proceed() 的結果")
        fun `should return result from chain proceed`() = runTest {
            // Given
            val originalUrl = "https://api.example.com/movies"
            val expectedLanguage = "en"
            val httpUrl = originalUrl.toHttpUrl()

            every { mockLanguageProvider.getLanguageCode() } returns expectedLanguage
            every { mockChain.request() } returns mockRequest
            every { mockRequest.url } returns httpUrl
            every { mockRequest.newBuilder() } returns Request.Builder().url(httpUrl)
            every { mockChain.proceed(any()) } returns mockResponse

            // When
            val result = languageInterceptor.intercept(mockChain)

            // Then
            expectThat(result).isEqualTo(mockResponse)
        }
    }
}
