package com.shang.model

data class MovieGenreBean(val genres: List<MovieGenre>) {
    /**
     * 電影類型
     * @param id 類型ID
     * @param name 類型名稱
     */
    data class MovieGenre(
        val id: Int,
        val name: String,
    )
}
