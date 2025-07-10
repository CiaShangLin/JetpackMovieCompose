package com.shang.model

/**
 * MovieCastBean 用於封裝電影演員與工作人員資料，對應網路層 MovieCastResponse。
 */
data class MovieCastBean(
    val cast: List<MovieCastResult> = emptyList(),
    val crew: List<CrewBean> = emptyList(),
    val id: Int = 0
) {

    /**
     * 工作人員資料 Bean
     */
    data class CrewBean(
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
        val profilePath: String = ""
    )
}