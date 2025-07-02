package com.shang.domain.usecase

import com.shang.common.UiState
import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<UiState<ConfigurationBean>> =
        // 從 repository 發出 Configuration 的遠端請求
        movieRepository.getConfiguration()
            .transform { state -> // transform 可彈性處理多種狀態與 emit 行為
                when (state) {
                    UiState.Loading -> {
                        // 若為 Loading 狀態，直接轉發給 UI
                        emit(UiState.Loading)
                    }

                    is UiState.Success -> {
                        // API 成功，將設定資料寫入本地快取（DataStore）
                        withContext(ioDispatcher) {
                            userDataRepository.setConfiguration(state.data)
                        }
                        // 再將資料傳給 UI 層
                        emit(state)
                    }

                    is UiState.Error -> {
                        // API 失敗，嘗試從 DataStore 中讀取快取資料
                        val config = withContext(ioDispatcher) {
                            userDataRepository.userData.firstOrNull()?.configuration
                        }

                        if (config != null) {
                            // 若有快取資料，則回傳 Success 狀態給 UI
                            emit(UiState.Success(config))
                        } else {
                            // 若沒有快取資料，則回傳原本的錯誤
                            emit(state)
                        }
                    }
                }
            }
}
