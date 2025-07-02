package com.shang.network.model

import com.shang.model.ConfigurationBean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 電影配置
 *
 * @param changeKeys 不知道是什麼
 * @param images
 */
@Serializable
data class ConfigurationResponse(
    @SerialName("change_keys")
    val changeKeys: List<String> = emptyList(),
    @SerialName("images")
    val images: Images = Images(),
) {
    /**
     * @param backdropSizes 背景size
     * @param posterSizes 海報size
     * @param baseUrl 組成圖片的host
     */
    @Serializable
    data class Images(
        @SerialName("backdrop_sizes")
        val backdropSizes: List<String> = emptyList(),
        @SerialName("base_url")
        val baseUrl: String = "",
        @SerialName("logo_sizes")
        val logoSizes: List<String> = emptyList(),
        @SerialName("poster_sizes")
        val posterSizes: List<String> = emptyList(),
        @SerialName("profile_sizes")
        val profileSizes: List<String> = emptyList(),
        @SerialName("secure_base_url")
        val secureBaseUrl: String = "",
        @SerialName("still_sizes")
        val stillSizes: List<String> = emptyList(),
    )
}

/**
 * 將網路模型轉換為外部模型
 */
fun ConfigurationResponse.asExternalModel(): ConfigurationBean {
    return ConfigurationBean(
        changeKeys = changeKeys,
        images = ConfigurationBean.Images(
            backdropSizes = images.backdropSizes,
            baseUrl = images.baseUrl,
            logoSizes = images.logoSizes,
            posterSizes = images.posterSizes,
            profileSizes = images.profileSizes,
            secureBaseUrl = images.secureBaseUrl,
            stillSizes = images.stillSizes,
        ),
    )
}
