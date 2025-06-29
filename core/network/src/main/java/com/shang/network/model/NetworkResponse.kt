package com.shang.network.model

data class NetworkResponse<out T>(
    val code: Int,
    val data: T? = null,
    val error: String? = null,
) {
    val isSuccess: Boolean
        get() = code in 200..299

    val errorMessage: String?
        get() = error ?: "Unknown error"
}
