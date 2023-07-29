package com.shang.jetpackmoviecompose.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow


typealias StatefulLiveData<T> = LiveData<UiState<T>>
typealias StatefulMutableLiveData<T> = MutableLiveData<UiState<T>>


sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val throwable: Throwable) : UiState<Nothing>()
    object Loading : UiState<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun failure(throwable: Throwable = Throwable()) = Failure(throwable)
        fun loading() = Loading
    }
}