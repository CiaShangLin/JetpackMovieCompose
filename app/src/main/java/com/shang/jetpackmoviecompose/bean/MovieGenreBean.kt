package com.shang.jetpackmovie.bean

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MovieGenreBean(
    val genres: List<Genre>
) {
    /**
     * 電影類型
     * @param id 類型ID
     * @param name 類型名稱
     */
    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int,
        val name: String
    ):Serializable
}