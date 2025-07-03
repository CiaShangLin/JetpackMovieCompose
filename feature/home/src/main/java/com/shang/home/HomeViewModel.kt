package com.shang.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.home.ui.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {

    val movieGenres = movieRepository.getMovieGenres()
        .map {
            it.fold(
                onSuccess = {
                    HomeUiState.Success(it)
                },
                onFailure = {
                    HomeUiState.Error(it.cause)
                },
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading,
        )

//    // {"id":28,"name":"Action"}
//
//    val configuration = getConfigurationUseCase()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = UiState.Loading,
//        )
//
//    val userData = userDataRepository.userData.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = null,
//    )
//
//    val discoverMovie = movieRepository.getDiscoverMovie(
//        withGenres = "28",
//        page = 1,
//    ).stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = UiState.Loading,
//    )
//
//    val pager = movieRepository.getMovieGenrePager("28")
//        .cachedIn(viewModelScope)
}
