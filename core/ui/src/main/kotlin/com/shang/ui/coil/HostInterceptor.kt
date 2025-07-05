package com.shang.ui.coil

import coil3.intercept.Interceptor
import coil3.request.ImageResult
import com.shang.data.repository.UserDataRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * 用於處理圖片請求的攔截器，主要是補上 host 前綴。
 */
class HostInterceptor @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request
        val url = request.data

        // 檢查是否需要補上 host
        if (url is String && !url.startsWith("http")) {
            val host = userDataRepository.userData.firstOrNull()?.configuration?.images?.baseUrl ?: ""

            if (host.isNotEmpty()) {
                val newUrl = "${host}original$url"

                val newRequest = request.newBuilder()
                    .data(newUrl)
                    .build()

                // 正確的 Coil API 用法：先設定新的 request，再 proceed
                return chain.withRequest(newRequest).proceed()
            }
        }

        // 如果不需要修改，直接 proceed
        return chain.proceed()
    }
}
