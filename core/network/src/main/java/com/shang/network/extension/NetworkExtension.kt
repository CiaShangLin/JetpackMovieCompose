package com.shang.network.extension

import com.shang.network.model.NetworkResponse
import retrofit2.Response

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
): NetworkResponse<T> {
    val response = apiCall.invoke()
    return if (response.isSuccessful) {
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
