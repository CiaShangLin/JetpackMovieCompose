package com.shang.network.retrofit

import com.shang.model.ConfigurationBean
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.model.MovieSearchBean
import com.shang.network.model.NetworkResponse

interface MovieDataSource {

    /**
     *  獲取電影配置
     */
    suspend fun getConfiguration(): NetworkResponse<ConfigurationBean>

    /**
     * 獲取電影類型
     * @return 電影類型列表
     */
    suspend fun getMovieGenres(): NetworkResponse<MovieGenreBean>

    /**
     * 獲取電影列表
     * @param withGenres 電影類型
     * @param page 分頁
     * @return 電影列表
     */
    suspend fun getDiscoverMovie(withGenres: String, page: Int): NetworkResponse<MovieListBean>

    /**
     * 搜尋電影
     * @param query 搜尋關鍵字
     * @param page 分頁
     */
    suspend fun getMovieSearch(query: String, page: Int): NetworkResponse<MovieSearchBean>

    /**
     * 獲取電影詳情
     * @param id 電影ID
     */
    suspend fun getMovieDetail(id: Int): NetworkResponse<MovieDetailBean>
}
