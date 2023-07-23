package com.shang.jetpackmoviecompose.api

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