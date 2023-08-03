package com.shang.jetpackmoviecompose.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shang.jetpackmovie.bean.IBaseMovie
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val mFavoritesRepository: FavoritesRepository
) : ViewModel(), IFavoritesRepository by mFavoritesRepository {


}