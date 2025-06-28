package com.shang.data.repository

import com.shang.model.MovieGenreBean

interface MovieRepository {
    suspend fun getMovieGenres(): MovieGenreBean
}
