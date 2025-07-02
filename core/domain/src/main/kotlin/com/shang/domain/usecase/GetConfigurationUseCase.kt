package com.shang.domain.usecase

import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
) {

    operator fun invoke(): Flow<UiState<ConfigurationBean>> {
        return movieRepository.getConfiguration()
//        movieRepository.getConfiguration().collect{
//            when(it){
//                is UiState.Error -> {
//
//                }
//                UiState.Loading -> {
//
//                }
//                is UiState.Success<ConfigurationBean> -> {
//
//                }
//            }
//        }
    }
}
