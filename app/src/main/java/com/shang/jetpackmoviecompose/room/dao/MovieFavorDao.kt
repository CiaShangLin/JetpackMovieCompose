package com.shang.jetpackmoviecompose.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity


@Dao
interface MovieFavorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieFavor(movieFavorEntity: MovieFavorEntity)

    @Delete
    fun deleteMovieFavor(movieFavorEntity: MovieFavorEntity)

    @Query("Select * From MovieFavorEntity Order by timestamp DESC")
    fun getAllMovieFavor(): LiveData<List<MovieFavorEntity>>

    @Query("Select *  From MovieFavorEntity Where id = :id")
    fun isFavorites(id: Int): Boolean
}