package com.shang.network.model

import android.annotation.SuppressLint
import com.shang.model.MovieGenreBean
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MovieGenreResponse(
    val genres: List<Genre>,
) {
    /**
     * 電影類型
     * @param id 類型ID
     * @param name 類型名稱
     */
    @Serializable
    data class Genre(
        val id: Int,
        val name: String,
    )
}

fun MovieGenreResponse.asExternalModel(): MovieGenreBean {
    return MovieGenreBean(
        genres = genres.map { MovieGenreBean.MovieGenre(it.id, it.name) },
    )
}
