package com.shang.model

/**
 * MovieCastBean 用於封裝電影演員與工作人員資料，對應網路層 MovieCastResponse。
 */
data class MovieCastAndCrewBean(
    val cast: List<Cast> = emptyList(),
    val crew: List<Crew> = emptyList(),
    val id: Int = 0,
) {

    data class Cast(
        val adult: Boolean = false,
        val castId: Int = 0,
        val character: String = "",
        val creditId: String = "",
        val gender: Int = 0,
        val id: Int = 0,
        val knownForDepartment: String = "",
        val name: String = "",
        val order: Int = 0,
        val originalName: String = "",
        val popularity: Double = 0.0,
        val profilePath: String = "",
    )

    /**
     * 工作人員資料 Bean
     */
    data class Crew(
        val adult: Boolean = false,
        val creditId: String = "",
        val department: String = "",
        val gender: Int = 0,
        val id: Int = 0,
        val job: String = "",
        val knownForDepartment: String = "",
        val name: String = "",
        val originalName: String = "",
        val popularity: Double = 0.0,
        val profilePath: String = "",
    )
}
