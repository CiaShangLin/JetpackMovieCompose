package com.shang.history.ui

import androidx.lifecycle.ViewModel
import com.shang.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val movieRepository: MovieRepository):
    ViewModel() {


}