package com.shang.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable) : UiState<Nothing>
    data object Loading : UiState<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<UiState<T>> = map<T, UiState<T>> {
    UiState.Success(it)
}
    .onStart {
        emit(UiState.Loading)
    }
    .catch {
        emit(UiState.Error(it))
    }
