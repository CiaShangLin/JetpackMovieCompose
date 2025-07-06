package com.shang.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shang.database.entity.MovieHistoryEntity

@Dao
interface MovieHistoryDao {

    @Query("SELECT * FROM MovieHistoryEntity")
    suspend fun getAllMovies(): List<MovieHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieHistoryEntity)
}
