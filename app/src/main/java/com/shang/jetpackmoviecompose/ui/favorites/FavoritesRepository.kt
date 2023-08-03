package com.shang.jetpackmoviecompose.ui.favorites

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.shang.jetpackmovie.bean.IBaseMovie
import com.shang.jetpackmoviecompose.room.dao.MovieFavorDao
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface IFavoritesRepository {
    fun insertMovieFavor(movie: IBaseMovie)
    fun deleteMovieFavor(movie: IBaseMovie)
    fun isFavoritesMovieFavor(id: Int): Flow<Boolean>
    fun getAllMovieFavorites(): Flow<List<MovieFavorEntity>>
}

class FavoritesRepository @Inject constructor(
    private val mMovieFavorDao: MovieFavorDao
) : IFavoritesRepository {
    override fun insertMovieFavor(movie: IBaseMovie) {
        mMovieFavorDao.insertMovieFavor(MovieFavorEntity.convert(movie))
    }

    override fun deleteMovieFavor(movie: IBaseMovie) {
        mMovieFavorDao.deleteMovieFavor(MovieFavorEntity.convert(movie))
    }

    override fun isFavoritesMovieFavor(id: Int): Flow<Boolean> {
        return mMovieFavorDao.isFavorites(id).map {
            it != null
        }
    }

    override fun getAllMovieFavorites(): Flow<List<MovieFavorEntity>> {
        return mMovieFavorDao.getAllMovieFavor()
    }

}