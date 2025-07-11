package com.shang.common.exception

import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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

/**
 * 將任意異常轉換為 NetworkException
 *
 * @return 對應的 NetworkException 實例
 */
fun Throwable.toNetworkException(): NetworkException {
    return when (this) {
        // 如果已經是 NetworkException 就直接返回
        is NetworkException -> this

        // 處理網路連線相關異常
        is ConnectException -> NetworkException.ConnectionError(this)
        is UnknownHostException -> NetworkException.ConnectionError(this)
        is NoRouteToHostException -> NetworkException.ConnectionError(this)

        // 處理超時相關異常
        is SocketTimeoutException -> NetworkException.TimeoutError(this)
        is InterruptedIOException -> NetworkException.TimeoutError(this)

        // 處理 HTTP 相關異常（使用更安全的反射）
        else -> {
            when {
                isHttpException() -> {
                    val httpCode = getHttpStatusCode()
                    val errorBody = getHttpErrorBody()
                    NetworkException.HttpError(
                        httpCode = httpCode,
                        errorBody = errorBody,
                    )
                }
                isJsonException() -> NetworkException.ParseError(this)
                else -> NetworkException.UnknownError(this)
            }
        }
    }
}

/**
 * 檢查是否為 HTTP 異常
 */
private fun Throwable.isHttpException(): Boolean {
    return this.javaClass.simpleName.equals("HttpException", ignoreCase = true) ||
        this.javaClass.name.contains("HttpException", ignoreCase = true)
}

/**
 * 安全地取得 HTTP 狀態碼
 */
private fun Throwable.getHttpStatusCode(): Int {
    return try {
        val method = this.javaClass.getMethod("code")
        method.invoke(this) as? Int ?: 0
    } catch (e: Exception) {
        // 嘗試其他可能的方法名稱
        try {
            val method = this.javaClass.getMethod("getCode")
            method.invoke(this) as? Int ?: 0
        } catch (e: Exception) {
            0
        }
    }
}

/**
 * 安全地取得 HTTP 錯誤內容
 */
private fun Throwable.getHttpErrorBody(): String? {
    return try {
        val method = this.javaClass.getMethod("response")
        val response = method.invoke(this)
        response?.javaClass?.getMethod("errorBody")?.invoke(response)?.toString()
    } catch (e: Exception) {
        this.message
    }
}

/**
 * 檢查是否為 JSON 解析異常
 */
private fun Throwable.isJsonException(): Boolean {
    val className = this.javaClass.simpleName
    return className.contains("Json", ignoreCase = true) ||
        className.contains("Parse", ignoreCase = true) ||
        className.contains("Serialization", ignoreCase = true)
}
