package com.shang.jetpackmoviecompose.ui.favorites

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.shang.jetpackmoviecompose.room.dao.MovieFavorDao
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
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

    fun getAllMovieFavor(): LiveData<List<MovieFavorEntity>> {
        return mMovieFavorDao.getAllMovieFavor()
    }
}