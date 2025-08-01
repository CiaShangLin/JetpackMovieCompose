package com.shang.history.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetHistoryMovieListUseCase
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    getHistoryMovieListUseCase: GetHistoryMovieListUseCase,
) :
    ViewModel() {

    val allMovieHistory = getHistoryMovieListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun toggleMovieCollectStatus(data: MovieCardData) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data.movieCardIsCollect) {
                movieRepository.deleteMovieCollect(data.asMovieCardResult())
            } else {
                movieRepository.insertMovieCollect(data.asMovieCardResult())
            }
        }
    }

    /**
     * 清空歷史紀錄
     */
    fun clearAllMovieHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDeleteSuccess = movieRepository.deleteAllMovieHistory()
        }
    }
}
