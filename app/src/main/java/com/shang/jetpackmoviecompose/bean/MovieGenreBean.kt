package com.shang.jetpackmovie.bean

import java.io.Serializable

data class MovieGenreBean(
    val genres: List<Genre>
) {
    /**
     * 電影類型
     * @param id 類型ID
     * @param name 類型名稱
     */
    data class Genre(
        val id: Int,
        val name: String
    ):Serializable
}