package com.shang.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.shang.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies(): List<MovieEntity>

//    @Insert
//    fun insertMovie(movie: MovieEntity)
}
