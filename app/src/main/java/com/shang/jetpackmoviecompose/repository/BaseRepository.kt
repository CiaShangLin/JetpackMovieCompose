package com.shang.jetpackmoviecompose.repository

import com.shang.jetpackmoviecompose.api.ApiException
import com.shang.jetpackmoviecompose.api.BaseResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

open class BaseRepository {

    suspend fun <T> requestResponse(requestCall: suspend () -> T): T {
        val response = withContext(Dispatchers.IO) {
            withTimeout(10 * 1000) {
                requestCall.invoke()
            }
        }
//        if (response.isFailed()) {
//            throw ApiException(response.errorCode, response.errorMsg)
//        }
        return response
    }

    suspend fun <T> requestResponse2(requestCall: suspend () -> ApiResponse<T>): ApiResponse<T> {
        val response = withContext(Dispatchers.IO) {
            withTimeout(10 * 1000) {
                requestCall.invoke()
            }
        }
        response.suspendOnSuccess {

        }.suspendOnFailure {

        }.suspendOnError {

        }

        return response
    }
}