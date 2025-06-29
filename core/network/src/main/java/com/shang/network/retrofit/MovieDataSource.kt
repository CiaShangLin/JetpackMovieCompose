package com.shang.network.retrofit

import com.shang.model.MovieGenreBean
import com.shang.network.model.NetworkResponse

interface MovieDataSource {
    /**
     * 獲取電影類型
     * @return 電影類型列表
     */
    suspend fun getMovieGenres(): NetworkResponse<MovieGenreBean>
}
