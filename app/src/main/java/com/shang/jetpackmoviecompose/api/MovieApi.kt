package com.shang.jetpackmoviecompose.api


import com.shang.jetpackmovie.bean.*
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenres():ApiResponse<MovieGenreBean>

    @GET("movie/{id}}")
    suspend fun getMovieDetail(@Path("id") id: Int): ApiResponse<MovieDetailBean>

    @GET("movie/{id}/credits")
    suspend fun getMovieActor(@Path("id") id: Int): ApiResponse<ActorBean>

//    similar
    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(@Path("id") id: Int): ApiResponse<MovieListBean>

    @GET("discover/movie")
    suspend fun getMovieGenreDetail(
        @Query("with_genres") with_genres: String,
        @Query("page") page: Int
    ): ApiResponse<MovieListBean>


    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationBean

    @GET
    fun getPicture(@Url url: String): Call<ResponseBody>
}