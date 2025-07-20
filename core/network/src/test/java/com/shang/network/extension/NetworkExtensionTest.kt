package com.shang.network.extension

import com.google.gson.JsonParseException
import com.shang.common.NetworkException
import com.shang.network.model.NetworkResponse
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * NetworkExtension 的單元測試
 *
 * 測試 safeApiCall 函數的各種異常情況和正常情況
 * 遵循 Given-When-Then 模式進行測試結構設計
 */
@DisplayName("NetworkExtension Tests")
class NetworkExtensionTest {

    private data class TestData(val id: Int, val name: String)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Nested
    @DisplayName("safeApiCall 成功情況測試")
    inner class SuccessfulApiCallTests {

        @Test
        @DisplayName("當 API 呼叫成功且有資料時，應該回傳包含資料的 NetworkResponse")
        fun `when api call is successful with data, should return NetworkResponse with data`() = runTest {
            // Given
            val testData = TestData(1, "test")
            val mockResponse = mockk<Response<TestData>>()
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.code() } returns 200
            every { mockResponse.body() } returns testData

            // When
            val result = safeApiCall { mockResponse }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(200)
                get { data }.isEqualTo(testData)
                get { error }.isNull()
                get { isSuccess }.isTrue()
            }
        }

        @Test
        @DisplayName("當 API 呼叫成功但無資料時，應該回傳空資料的 NetworkResponse")
        fun `when api call is successful but no data, should return NetworkResponse with null data`() = runTest {
            // Given
            val mockResponse = mockk<Response<TestData>>()
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.code() } returns 204
            every { mockResponse.body() } returns null

            // When
            val result = safeApiCall { mockResponse }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(204)
                get { data }.isNull()
                get { error }.isNull()
                get { isSuccess }.isFalse() // 因為 data 為 null
            }
        }
    }

    @Nested
    @DisplayName("safeApiCall HTTP 錯誤測試")
    inner class HttpErrorTests {

        @Test
        @DisplayName("當 API 回傳 4xx 錯誤時，應該回傳包含 HttpError 的 NetworkResponse")
        fun `when api returns 4xx error, should return NetworkResponse with HttpError`() = runTest {
            // Given
            val errorBody = "Bad Request"
            val mockErrorBody = mockk<okhttp3.ResponseBody>()
            val mockResponse = mockk<Response<TestData>>()

            every { mockResponse.isSuccessful } returns false
            every { mockResponse.code() } returns 400
            every { mockResponse.errorBody() } returns mockErrorBody
            every { mockErrorBody.string() } returns errorBody

            // When
            val result = safeApiCall { mockResponse }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(400)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.HttpError>().and {
                    get { httpCode }.isEqualTo(400)
                    get { errorBody }.isEqualTo("Bad Request")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當 API 回傳 5xx 錯誤時，應該回傳包含 HttpError 的 NetworkResponse")
        fun `when api returns 5xx error, should return NetworkResponse with HttpError`() = runTest {
            // Given
            val mockResponse = mockk<Response<TestData>>()
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.code() } returns 500
            every { mockResponse.errorBody() } returns null

            // When
            val result = safeApiCall { mockResponse }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(500)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.HttpError>().and {
                    get { httpCode }.isEqualTo(500)
                    get { errorBody }.isNull()
                }
                get { isSuccess }.isFalse()
            }
        }
    }

    @Nested
    @DisplayName("safeApiCall 網路異常測試")
    inner class NetworkExceptionTests {

        @Test
        @DisplayName("當發生 SocketTimeoutException 時，應該回傳包含 TimeoutError 的 NetworkResponse")
        fun `when SocketTimeoutException occurs, should return NetworkResponse with TimeoutError`() = runTest {
            // Given
            val timeoutException = SocketTimeoutException("Connection timeout")

            // When
            val result = safeApiCall<SocketTimeoutException> { throw timeoutException }

            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.TimeoutError>().and {
                    get { cause }.isEqualTo(timeoutException)
                    get { message }.isEqualTo("Request timeout")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當發生 ConnectException 時，應該回傳包含 ConnectionError 的 NetworkResponse")
        fun `when ConnectException occurs, should return NetworkResponse with ConnectionError`() = runTest {
            // Given
            val connectException = ConnectException("Connection refused")

            // When
            val result = safeApiCall<ConnectException> { throw connectException }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.ConnectionError>().and {
                    get { cause }.isEqualTo(connectException)
                    get { message }.isEqualTo("Connection failed")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當發生 UnknownHostException 時，應該回傳包含 ConnectionError 的 NetworkResponse")
        fun `when UnknownHostException occurs, should return NetworkResponse with ConnectionError`() = runTest {
            // Given
            val hostException = UnknownHostException("Unable to resolve host")

            // When
            val result = safeApiCall<UnknownHostException> { throw hostException }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.ConnectionError>().and {
                    get { cause }.isEqualTo(hostException)
                    get { message }.isEqualTo("Connection failed")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當發生 JsonParseException 時，應該回傳包含 ParseError 的 NetworkResponse")
        fun `when JsonParseException occurs, should return NetworkResponse with ParseError`() = runTest {
            // Given
            val parseException = JsonParseException("Invalid JSON format")

            // When
            val result = safeApiCall<JsonParseException> {
                throw parseException
            }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.ParseError>().and {
                    get { cause }.isEqualTo(parseException)
                    get { message }.isEqualTo("Parse failed")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當發生 IOException 時，應該回傳包含 ConnectionError 的 NetworkResponse")
        fun `when IOException occurs, should return NetworkResponse with ConnectionError`() = runTest {
            // Given
            val ioException = IOException("I/O error occurred")

            // When
            val result = safeApiCall<IOException> { throw ioException }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.ConnectionError>().and {
                    get { cause }.isEqualTo(ioException)
                    get { message }.isEqualTo("Connection failed")
                }
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當發生未知異常時，應該回傳包含 UnknownError 的 NetworkResponse")
        fun `when unknown exception occurs, should return NetworkResponse with UnknownError`() = runTest {
            // Given
            val unknownException = RuntimeException("Unknown error")

            // When
            val result = safeApiCall<RuntimeException> { throw unknownException }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(-1)
                get { data }.isNull()
                get { error }.isNotNull().isA<NetworkException.UnknownError>().and {
                    get { cause }.isEqualTo(unknownException)
                }
                get { isSuccess }.isFalse()
            }
        }
    }

    @Nested
    @DisplayName("mapData 函數測試")
    inner class MapDataTests {

        @Test
        @DisplayName("當 NetworkResponse 有資料時，應該成功轉換資料型別")
        fun `when NetworkResponse has data, should successfully transform data type`() {
            // Given
            val originalData = TestData(1, "test")
            val networkResponse = NetworkResponse(200, originalData, null)

            // When
            val result = networkResponse.mapData { it.name.uppercase() }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(200)
                get { data }.isEqualTo("TEST")
                get { error }.isNull()
                get { isSuccess }.isTrue()
            }
        }

        @Test
        @DisplayName("當 NetworkResponse 無資料時，應該保留原有錯誤資訊")
        fun `when NetworkResponse has no data, should preserve original error`() {
            // Given
            val originalError = NetworkException.HttpError(404, "Not found")
            val networkResponse = NetworkResponse<TestData>(404, null, originalError)

            // When
            val result = networkResponse.mapData { it.name.uppercase() }

            // Then
            expectThat(result) {
                get { code }.isEqualTo(404)
                get { data }.isNull()
                get { error }.isEqualTo(originalError)
                get { isSuccess }.isFalse()
            }
        }

        @Test
        @DisplayName("當 NetworkResponse 資料為 null 時，不應該執行轉換函數")
        fun `when NetworkResponse data is null, should not execute transform function`() {
            // Given
            val networkResponse = NetworkResponse<TestData>(200, null, null)
            var transformCalled = false

            // When
            val result = networkResponse.mapData {
                transformCalled = true
                it.name.uppercase()
            }

            // Then
            expectThat(transformCalled).isFalse()
            expectThat(result) {
                get { code }.isEqualTo(200)
                get { data }.isNull()
                get { error }.isNull()
                get { isSuccess }.isFalse()
            }
        }
    }
}
