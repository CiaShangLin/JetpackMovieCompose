package com.shang.jetpackmoviecompose.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val mFavoritesRepository: FavoritesRepository
) : ViewModel() {

    fun insertMovieFavor(movieFavorEntity: MovieFavorEntity) {
        mFavoritesRepository.insertMovieFavor(movieFavorEntity)
    }

    fun deleteMovieFavor(movieFavorEntity: MovieFavorEntity) {
        mFavoritesRepository.deleteMovieFavor(movieFavorEntity)
    }

    fun getAllMovieFavor(): LiveData<List<MovieFavorEntity>> {
        return mFavoritesRepository.getAllMovieFavor()
    }
}