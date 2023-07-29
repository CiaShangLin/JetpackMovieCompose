package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shang.jetpackmoviecompose.ui.home.HomeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MainViewModel @AssistedInject constructor(
    private val repository: HomeRepository,
    @Assisted
    private val movieId: Int,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int) : MainViewModel
    }

    companion object {
        fun provideMainViewModelFactory(factory: Factory, movieId: Int) : ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(movieId) as T
                }
            }
        }
    }
}