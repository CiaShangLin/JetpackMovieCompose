package com.shang.jetpackmoviecompose.api


import com.shang.jetpackmovie.bean.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): MovieGenreBean

    @GET("movie/{id}}")
    suspend fun getMovieDetail(@Path("id") id: Int): MovieDetailBean

    @GET("movie/{id}/credits")
    suspend fun getMovieActor(@Path("id") id: Int): ActorBean

//    similar
    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(@Path("id") id: Int): MovieListBean

    @GET("discover/movie")
    suspend fun getMovieGenreDetail(
        @Query("with_genres") with_genres: String,
        @Query("page") page: Int
    ): MovieListBean


    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationBean

    @GET
    fun getPicture(@Url url: String): Call<ResponseBody>
}