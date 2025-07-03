package com.shang.network.model

import com.shang.network.retrofit.NetworkException

/**
 * 統一封裝網路請求回應的資料結構。
 *
 * 1. 以泛型 T 表示成功時的資料型別，錯誤時則帶有 NetworkException。
 * 2. code 對應 HTTP 狀態碼，便於上層根據狀態碼進行分流處理。
 * 3. isSuccess 屬性可快速判斷是否為成功且有資料的回應。
 *
 * @param code HTTP 狀態碼或自定義錯誤碼
 * @param data 請求成功時的資料內容
 * @param error 請求失敗時的錯誤資訊
 */
data class NetworkResponse<out T>(
    val code: Int,
    val data: T? = null,
    val error: NetworkException? = null,
) {
    /**
     * 判斷回應是否為成功且有資料。
     * 僅當 code 在 200~299 且 data 不為 null 時回傳 true。
     */
    val isSuccess: Boolean
        get() = code in 200..299 && data != null
}

// fun <T> NetworkResponse<T>.asFlow(): Flow<Result<T>> {
//    return flow {
//        if (isSuccess) {
//            emit(Result.success(data!!))
//        } else {
//            emit(Result.failure(error!!))
//        }
//    }
// }
