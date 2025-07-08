package com.shang.model

/**
 * 電影類型數據模型
 * @param genres 電影類型列表
 */
data class MovieGenreBean(val genres: List<MovieGenre> = listOf()) {
    /**
     * 電影類型
     * @param id 類型ID
     * @param name 類型名稱
     */
    data class MovieGenre(
        val id: Int = 0,
        val name: String = "",
    )
}
