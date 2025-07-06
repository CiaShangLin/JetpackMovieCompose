package com.shang.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shang.database.entity.MovieCollectEntity

@Dao
interface MovieCollectDao {

    @Query("SELECT * FROM MovieCollectEntity")
    suspend fun getAllMovies(): List<MovieCollectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieCollectEntity)
}
