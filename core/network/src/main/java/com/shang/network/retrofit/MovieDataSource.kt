package com.shang.network.retrofit

import com.shang.network.model.MovieGenreResponse

interface MovieDataSource {
    /**
     * 獲取電影類型
     * @return 電影類型列表
     */
    suspend fun getMovieGenres(): MovieGenreResponse
}
