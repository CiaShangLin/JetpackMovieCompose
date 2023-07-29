package com.shang.jetpackmovie.bean

import com.squareup.moshi.JsonClass

/**
 * 電影列表
 *
 * @param page 頁數
 * @param results 資料
 * @param total_pages 總頁數
 * @param total_results 全部資料的數量
 */
@JsonClass(generateAdapter = true)
data class MovieListBean(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {

    /**
     * @param adult 是否成人
     * @param backdrop_path 背景海報
     * @param genre_ids 類型id
     * @param id 電影ID
     * @param original_language 原始語言
     * @param original_title 原始標題
     * @param overview 概要
     * @param popularity 人氣趨勢
     * @param poster_path 海報
     * @param release_date 上映日
     * @param title 標題
     * @param video 影片但不知道這個欄位是什麼意思
     * @param vote_average 人氣指數
     * @param vote_count 投票次數
     */
    @JsonClass(generateAdapter = true)
    data class Result(
        val adult: Boolean?,
        val backdrop_path: String?,
        val genre_ids: List<Int>?,
        val id: Int?,
        val original_language: String?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val poster_path: String?,
        val release_date: String?,
        val title: String?,
        val video: Boolean?,
        val vote_average: Double?,
        val vote_count: Int?
    ) : IBaseMovie {

        override fun getMovieID(): Int = id?:-1

        override fun getMovieTitle(): String = title?:""

        override fun getPosterPath(): String = poster_path?:""

        override fun getVoteAverage(): Double = vote_average?:0.0

        override fun getReleaseDate(): String = release_date?:""

        /**
         * 當某個欄位是null時hashCode會丟出NEP導致Epoxy在綁定data時發生錯誤
         * 解決方法
         * 1.kotlin降低到1.4.0
         * 2.所有欄位補上nullable
         * 3.hashCode直接回傳id
         */
        //override fun hashCode(): Int = id
    }
}