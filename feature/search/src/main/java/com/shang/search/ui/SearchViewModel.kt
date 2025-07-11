package com.shang.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shang.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _searchQueryFlow = MutableStateFlow<String>("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "",
    )

    val movieSearchPager = _searchQueryFlow
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isNotEmpty()) {
                movieRepository.getMovieSearchPager(query)
                    .cachedIn(viewModelScope)
            } else {
                flowOf(PagingData.Companion.empty())
            }
        }

    /**
     * 觸發搜尋，並根據查詢字串更新 UI 狀態
     * 若查詢為空，回到 Idle 狀態
     * @param query 使用者輸入的查詢字串
     */
    fun startSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQueryFlow.value = query
        }
    }
}