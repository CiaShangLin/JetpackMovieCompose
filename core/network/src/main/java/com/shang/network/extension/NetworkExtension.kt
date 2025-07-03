package com.shang.network.extension

import com.google.gson.JsonParseException
import com.shang.network.model.NetworkResponse
import com.shang.network.retrofit.NetworkException
import okio.IOException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 安全執行 Retrofit API 請求的協程輔助方法。
 *
 * 1. 捕捉常見網路異常（如超時、連線失敗、解析錯誤等），並轉換為結構化 NetworkResponse。
 * 2. 保證不會拋出未處理例外，便於上層統一處理錯誤。
 * 3. 支援單元測試與可擴展性，符合 Clean Architecture 原則。
 *
 * @param apiCall suspend lambda，執行 Retrofit API 請求
 * @return NetworkResponse<T>，包含成功資料或錯誤資訊
 */
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
                error = NetworkException.HttpError(response.code(), response.errorBody()?.string()),
            )
        }
    } catch (e: SocketTimeoutException) {
        NetworkResponse(code = -1, error = NetworkException.TimeoutError(e))
    } catch (e: ConnectException) {
        NetworkResponse(code = -1, error = NetworkException.ConnectionError(e))
    } catch (e: UnknownHostException) {
        NetworkResponse(code = -1, error = NetworkException.ConnectionError(e))
    } catch (e: JsonParseException) {
        NetworkResponse(code = -1, error = NetworkException.ParseError(e))
    } catch (e: IOException) {
        NetworkResponse(code = -1, error = NetworkException.ConnectionError(e))
    } catch (e: Exception) {
        NetworkResponse(code = -1, error = NetworkException.UnknownError(e))
    }
}

/**
 * 將 NetworkResponse<T> 內的資料型別轉換為另一型別，保留錯誤資訊。
 *
 * 1. 僅在 data 不為 null 時執行 transform，否則錯誤資訊原樣傳遞。
 * 2. 適用於 Repository/UseCase 層資料轉換，提升可讀性與可測試性。
 *
 * @param transform 資料轉換 lambda
 * @return NetworkResponse<R>，轉換後的新型別資料或原錯誤
 */
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
