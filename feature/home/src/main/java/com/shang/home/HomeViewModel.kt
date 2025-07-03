package com.shang.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
import com.shang.model.ConfigurationBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getConfigurationUseCase: GetConfigurationUseCase,
    private val userDataRepository: UserDataRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {
    // {"id":28,"name":"Action"}

    val configuration: StateFlow<UiState<ConfigurationBean>> = getConfigurationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading,
        )

    val userData = userDataRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    val discoverMovie = movieRepository.getDiscoverMovie(
        withGenres = "28",
        page = 1,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading,
    )

    val pager = movieRepository.getMovieGenrePager("28")
        .cachedIn(viewModelScope)
}
