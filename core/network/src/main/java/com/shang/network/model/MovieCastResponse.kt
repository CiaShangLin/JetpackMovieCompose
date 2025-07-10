package com.shang.network.model

import android.annotation.SuppressLint
import com.shang.model.MovieCastResult
import com.shang.model.MovieCastBean
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 *
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MovieCastResponse(
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

fun MovieCastResponse.asExternalModel(): MovieCastBean {
    return MovieCastBean(
        cast = cast?.map { it.asExternalModel() } ?: emptyList(),
        crew = crew?.map { it.asExternalModel() } ?: emptyList(),
        id = id ?: 0
    )
}

private fun MovieCastResponse.Cast.asExternalModel(): MovieCastResult{
    return MovieCastResult(
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

private fun MovieCastResponse.Crew.asExternalModel(): MovieCastBean.CrewBean {
    return MovieCastBean.CrewBean(
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
