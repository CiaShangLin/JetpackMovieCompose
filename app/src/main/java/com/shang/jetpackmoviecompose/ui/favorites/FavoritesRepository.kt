package com.shang.jetpackmoviecompose.ui.favorites

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.shang.jetpackmoviecompose.room.dao.MovieFavorDao
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val mMovieFavorDao: MovieFavorDao
) {
    fun insertMovieFavor(movieFavorEntity: MovieFavorEntity) {
        mMovieFavorDao.insertMovieFavor(movieFavorEntity)
    }

    fun deleteMovieFavor(movieFavorEntity: MovieFavorEntity) {
        mMovieFavorDao.deleteMovieFavor(movieFavorEntity)
    }

    fun getAllMovieFavor(): Flow<List<MovieFavorEntity>> {
        return mMovieFavorDao.getAllMovieFavor()
    }
}