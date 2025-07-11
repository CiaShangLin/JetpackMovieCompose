package com.shang.common.exception

/**
 * 網路層異常的封裝類別
 *
 * 提供統一的網路錯誤處理，包含 HTTP 錯誤、連線錯誤、超時錯誤等
 * 所有網路相關異常都繼承自此類別，便於統一處理
 */
sealed class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    /**
     * HTTP 狀態碼錯誤
     * @param httpCode HTTP 狀態碼
     * @param errorBody 錯誤回應內容
     */
    data class HttpError(
        val httpCode: Int,
        val errorBody: String? = null,
    ) : NetworkException("HTTP Error: $httpCode")

    /**
     * 網路連線錯誤
     * @param cause 原始異常
     */
    data class ConnectionError(
        override val cause: Throwable,
    ) : NetworkException("Connection failed", cause)

    /**
     * 請求超時錯誤
     * @param cause 原始異常
     */
    data class TimeoutError(
        override val cause: Throwable,
    ) : NetworkException("Request timeout", cause)

    /**
     * 資料解析錯誤
     * @param cause 原始異常
     */
    data class ParseError(
        override val cause: Throwable,
    ) : NetworkException("Parse failed", cause)

    /**
     * 未知錯誤
     * @param cause 原始異常
     */
    data class UnknownError(
        override val cause: Throwable,
    ) : NetworkException("Unknown error", cause)
}
