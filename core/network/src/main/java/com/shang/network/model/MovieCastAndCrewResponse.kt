package com.shang.network.model

import android.annotation.SuppressLint
import com.shang.model.MovieCastAndCrewBean
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * 電影演員與工作人員資料的數據模型。
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MovieCastAndCrewResponse(
    @SerialName("cast")
    val cast: List<Cast>? = listOf(),
    @SerialName("crew")
    val crew: List<Crew>? = listOf(),
    @SerialName("id")
    val id: Int? = 0
) {

    @Serializable
    data class Cast(
        @SerialName("adult")
        val adult: Boolean? = false,
        @SerialName("cast_id")
        val castId: Int? = 0,
        @SerialName("character")
        val character: String? = "",
        @SerialName("credit_id")
        val creditId: String? = "",
        @SerialName("gender")
        val gender: Int? = 0,
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("known_for_department")
        val knownForDepartment: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("order")
        val order: Int? = 0,
        @SerialName("original_name")
        val originalName: String? = "",
        @SerialName("popularity")
        val popularity: Double? = 0.0,
        @SerialName("profile_path")
        val profilePath: String? = ""
    )

    @Serializable
    data class Crew(
        @SerialName("adult")
        val adult: Boolean? = false,
        @SerialName("credit_id")
        val creditId: String? = "",
        @SerialName("department")
        val department: String? = "",
        @SerialName("gender")
        val gender: Int? = 0,
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("job")
        val job: String? = "",
        @SerialName("known_for_department")
        val knownForDepartment: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("original_name")
        val originalName: String? = "",
        @SerialName("popularity")
        val popularity: Double? = 0.0,
        @SerialName("profile_path")
        val profilePath: String? = ""
    )
}

fun MovieCastAndCrewResponse.asExternalModel(): MovieCastAndCrewBean {
    return MovieCastAndCrewBean(
        cast = cast?.map { it.asExternalModel() } ?: emptyList(),
        crew = crew?.map { it.asExternalModel() } ?: emptyList(),
        id = id ?: 0
    )
}

private fun MovieCastAndCrewResponse.Cast.asExternalModel(): MovieCastAndCrewBean.Cast {
    return MovieCastAndCrewBean.Cast (
        adult = adult ?: false,
        castId = castId ?: 0,
        character = character ?: "",
        creditId = creditId ?: "",
        gender = gender ?: 0,
        id = id ?: 0,
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        order = order ?: 0,
        originalName = originalName ?: "",
        popularity = popularity ?: 0.0,
        profilePath = profilePath ?: ""
    )
}

private fun MovieCastAndCrewResponse.Crew.asExternalModel(): MovieCastAndCrewBean.Crew {
    return MovieCastAndCrewBean.Crew(
        adult = adult ?: false,
        creditId = creditId ?: "",
        department = department ?: "",
        gender = gender ?: 0,
        id = id ?: 0,
        job = job ?: "",
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        originalName = originalName ?: "",
        popularity = popularity ?: 0.0,
        profilePath = profilePath ?: ""
    )
}
