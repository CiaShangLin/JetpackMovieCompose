package com.shang.model

/**
 * 演員資料 Bean
 */
data class MovieCastResult(
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
    val profilePath: String = ""
)