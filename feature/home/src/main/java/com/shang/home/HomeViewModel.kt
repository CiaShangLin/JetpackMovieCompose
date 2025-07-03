package com.shang.home

import androidx.lifecycle.ViewModel
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {

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
