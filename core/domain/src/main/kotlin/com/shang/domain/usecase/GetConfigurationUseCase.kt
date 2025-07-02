package com.shang.domain.usecase

import com.shang.common.UiState
import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<UiState<ConfigurationBean>> {
        return movieRepository.getConfiguration()
//        return flow {
//            movieRepository.getConfiguration().collect {
//                when (it) {
//                    is UiState.Error -> emit(it)
//                    UiState.Loading -> emit(UiState.Loading)
//                    is UiState.Success<ConfigurationBean> -> {
//                        userDataRepository.setConfiguration(it.data)
//                        emit(it)
//                    }
//                }
//            }
//        }.flowOn(ioDispatcher)
    }
}
