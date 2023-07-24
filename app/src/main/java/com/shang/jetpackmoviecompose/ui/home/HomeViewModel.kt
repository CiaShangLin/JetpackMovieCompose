package com.shang.jetpackmoviecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.ui.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject() constructor(
    private val mHomeRepository: HomeRepository
) : ViewModel() {

    val flow = MutableSharedFlow<MovieGenreBean?>()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            try {
                val data = mHomeRepository.getMovieGenres()
                flow.emit(data)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
}