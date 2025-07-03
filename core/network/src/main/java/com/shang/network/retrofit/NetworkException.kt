package com.shang.network.retrofit

sealed class NetworkException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    // HTTP 錯誤
    data class HttpError(val httpCode: Int, val errorBody: String? = null) : NetworkException("HTTP Error: $httpCode")

    // 連線錯誤
    data class ConnectionError(override val cause: Throwable) : NetworkException("Connection failed", cause)

    // 超時錯誤
    data class TimeoutError(override val cause: Throwable) : NetworkException("Request timeout", cause)

    // 解析錯誤
    data class ParseError(override val cause: Throwable) : NetworkException("Parse failed", cause)

    // 未知錯誤
    data class UnknownError(override val cause: Throwable) : NetworkException("Unknown error", cause)
}
