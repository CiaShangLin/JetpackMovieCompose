package com.shang.network.extension

import com.shang.network.model.NetworkResponse
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
): NetworkResponse<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            NetworkResponse(
                code = response.code(),
                data = response.body(),
            )
        } else {
            NetworkResponse(
                code = response.code(),
                error = response.errorBody()?.string(),
            )
        }
    }  catch (e: IOException) {
        // 網路失敗，如連線錯誤、timeout
        NetworkResponse(
            code = -1,
            error = "NETWORK_ERROR: ${e.message}",
        )
    } catch (e: HttpException) {
        // retrofit 拋出 HTTP 相關錯誤（如 500，但其實 response.errorBody 應該能處理）
        NetworkResponse(
            code = e.code(),
            error = "HTTP_EXCEPTION: ${e.message}",
        )
    } catch (e: Exception) {
        NetworkResponse(
            code = -1,
            error = "UNKNOWN_ERROR: ${e.message}",
        )
    }
}

inline fun <T, R> NetworkResponse<T>.mapData(transform: (T) -> R): NetworkResponse<R> {
    return if (data != null) {
        NetworkResponse(
            code = code,
            data = transform(data),
            error = null,
        )
    } else {
        NetworkResponse(
            code = code,
            data = null,
            error = error,
        )
    }
}
