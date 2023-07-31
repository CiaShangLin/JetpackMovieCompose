package com.shang.jetpackmovie.bean

import com.squareup.moshi.JsonClass

/**
 * 電影配置
 *
 * @param change_keys 不知道是什麼
 * @param images
 */
@JsonClass(generateAdapter = true)
data class ConfigurationBean(
    val change_keys: List<String>,
    val images: Images
) {

    /**
     * @param backdrop_sizes 背景size
     * @param poster_sizes 海報size
     * @param base_url 組成圖片的host
     */
    @JsonClass(generateAdapter = true)
    data class Images(
        val backdrop_sizes: List<String>,
        val base_url: String,
        val logo_sizes: List<String>,
        val poster_sizes: List<String>,
        val profile_sizes: List<String>,
        val secure_base_url: String,
        val still_sizes: List<String>
    )
}