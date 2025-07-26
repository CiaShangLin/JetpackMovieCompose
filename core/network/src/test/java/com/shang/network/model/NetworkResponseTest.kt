package com.shang.network.model

import com.shang.common.NetworkException
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import strikt.assertions.isTrue

@DisplayName("NetworkResponse 單元測試")
class NetworkResponseTest {

    @Nested
    @DisplayName("isSuccess 屬性測試")
    inner class IsSuccessProperty {

        @Test
        @DisplayName("當 code 為 200 且 data 不為 null，isSuccess 應為 true")
        fun `isSuccess should return true when code is 200 and data is not null`() {
            // Given
            val successResponse = NetworkResponse(code = 200, data = "Success")

            // When
            val isSuccess = successResponse.isSuccess

            // Then
            expectThat(isSuccess).isTrue()
        }

        @Test
        @DisplayName("當 code 不在 200-299 範圍內，isSuccess 應為 false")
        fun `isSuccess should return false when code is not in 200-299 range`() {
            // Given
            val errorResponse = NetworkResponse<String>(code = 404, error = mockk<NetworkException.HttpError>())

            // When
            val isSuccess = errorResponse.isSuccess

            // Then
            expectThat(isSuccess).isFalse()
        }

        @Test
        @DisplayName("當 data 為 null，isSuccess 應為 false")
        fun `isSuccess should return false when data is null`() {
            // Given
            val responseWithNullData = NetworkResponse<String>(code = 200, data = null)

            // When
            val isSuccess = responseWithNullData.isSuccess

            // Then
            expectThat(isSuccess).isFalse()
        }

        @Test
        @DisplayName("當 code 在 200-299 範圍內，isSuccess 應為 true")
        fun `isSuccess should return true for any code in 200-299 range`() {
            // Given
            val response201 = NetworkResponse(code = 201, data = "Created")
            val response299 = NetworkResponse(code = 299, data = 123)

            // When
            val isSuccess201 = response201.isSuccess
            val isSuccess299 = response299.isSuccess

            // Then
            expectThat(isSuccess201).isTrue()
            expectThat(isSuccess299).isTrue()
        }
    }

    @Nested
    @DisplayName("資料持有測試")
    inner class DataHolding {

        @Test
        @DisplayName("成功的回應應正確持有資料")
        fun `response should correctly hold data`() {
            // Given
            val data = "Test Data"
            val response = NetworkResponse(code = 200, data = data)

            // Then
            expectThat(response) {
                get { this.data }.isEqualTo(data)
                get { this.code }.isEqualTo(200)
                get { this.error }.isNull()
            }
        }

        @Test
        @DisplayName("失敗的回應應正確持有錯誤資訊")
        fun `response should correctly hold error`() {
            // Given
            val error = mockk<NetworkException.HttpError>()
            val response = NetworkResponse<String>(code = 500, error = error)

            // Then
            expectThat(response) {
                get { this.error }.isEqualTo(error)
                get { this.code }.isEqualTo(500)
                get { this.data }.isNull()
            }
        }
    }
}
