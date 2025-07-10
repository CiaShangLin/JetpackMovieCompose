package com.shang.ui.coil

import coil3.intercept.Interceptor
import coil3.request.ImageResult
import com.shang.common.di.ApplicationScope
import com.shang.data.repository.UserDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 用於處理圖片請求的攔截器，主要是補上 host 前綴。
 *
 * 功能：
 * - 監聽使用者設定中的 baseUrl 變化
 * - 自動為圖片 URL 添加 host 前綴和 "original" 路徑
 * - 處理相對路徑和絕對路徑的 URL
 */
class HostInterceptor @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : Interceptor {

    @Volatile
    private var baseUrl: String = ""

    init {
        applicationScope.launch {
            userDataRepository.userData.map {
                it.configuration.images.baseUrl
            }.distinctUntilChanged().collect { url ->
                baseUrl = url.let {
                    // 確保 baseUrl 以 / 結尾
                    if (it.isNotEmpty() && !it.endsWith("/")) "$it/" else it
                }
            }
        }
    }

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request
        val originalUrl = request.data.toString()

        // 檢查是否需要添加 baseUrl
        val newUrl = when {
            // 如果 baseUrl 為空，直接使用原始 URL
            baseUrl.isEmpty() -> originalUrl

            // 如果已經是完整的 URL（包含 http/https），直接使用
            originalUrl.startsWith("http://") || originalUrl.startsWith("https://") -> originalUrl

            // 如果是相對路徑，添加 baseUrl 和 original 前綴
            else -> "${baseUrl}original$originalUrl"
        }

        // 只有在 URL 有變化時才建立新的請求
        return if (newUrl != originalUrl) {
            val newRequest = request.newBuilder()
                .data(newUrl)
                .build()
            chain.withRequest(newRequest).proceed()
        } else {
            chain.proceed()
        }
    }
}
