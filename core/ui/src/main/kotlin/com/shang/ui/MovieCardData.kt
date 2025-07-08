package com.shang.ui

import com.shang.model.MovieListBean
import com.shang.model.MovieSearchBean

/**
 * 代表電影卡片顯示所需的資料結構
 * @author Copilot
 */
data class MovieCardData(
    /** 電影唯一識別碼 */
    val movieCardId: Int,
    /** 電影標題 */
    val movieCardTitle: String,
    /** 海報圖片路徑 */
    val movieCardPosterPath: String,
    /** 上映日期 */
    val movieCardReleaseDate: String,
    /** 評分 */
    val movieCardVoteAverage: Double,
    /** 收藏狀態 */
    val movieCardIsCollect: Boolean,
    /** 收藏時間戳 */
    val movieCardTimestamp: Long
)

fun MovieListBean.Result.asMovieCardData(): MovieCardData =
    MovieCardData(
        movieCardId = id,
        movieCardTitle = title,
        movieCardPosterPath = posterPath,
        movieCardReleaseDate = releaseDate,
        movieCardVoteAverage = voteAverage,
        movieCardIsCollect = isCollect,
        movieCardTimestamp = timestamp
    )

fun MovieSearchBean.Result.asMovieCardData(): MovieCardData =
    MovieCardData(
        movieCardId = id,
        movieCardTitle = title,
        movieCardPosterPath = posterPath,
        movieCardReleaseDate = releaseDate,
        movieCardVoteAverage = voteAverage,
        movieCardIsCollect = isCollect,
        movieCardTimestamp = timestamp
    )