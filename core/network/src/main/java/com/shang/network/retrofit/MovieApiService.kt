package com.shang.network.retrofit

import com.shang.network.model.ConfigurationResponse
import com.shang.network.model.DiscoverMovieResponse
import com.shang.network.model.MovieGenreResponse
import com.shang.network.model.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    /**
     * 獲取配置
     */
    @GET("configuration")
    suspend fun getConfiguration(): Response<ConfigurationResponse>

    /**
     * 首頁電影類型標籤
     */
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<MovieGenreResponse>

    /**
     * 獲取電影列表
     * @param withGenres 電影類型
     * @param page 分頁
     * @return 電影列表
     */
    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int,
    ): Response<DiscoverMovieResponse>

    /**
     * 搜尋電影
     * @param query 搜尋關鍵字
     * @param page 分頁
     */
    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<SearchMovieResponse>

//    @GET("movie/{id}}")
//    suspend fun getMovieDetail(
//        @Path("id") id: Int,
//    ): MovieDetailBean
//
//    @GET("movie/{id}/credits")
//    suspend fun getMovieActor(
//        @Path("id") id: Int,
//    ): ActorBean
//
//    //    similar
//    @GET("movie/{id}/recommendations")
//    suspend fun getMovieRecommendations(
//        @Path("id") id: Int,
//    ): MovieListBean

//
//    @GET
//    fun getPicture(
//        @Url url: String,
//    ): Call<ResponseBody>
}
