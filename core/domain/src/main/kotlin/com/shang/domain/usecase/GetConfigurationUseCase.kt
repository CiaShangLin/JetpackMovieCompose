package com.shang.domain.usecase

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetConfigurationUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<Result<ConfigurationBean>> =
        // 從 repository 發出 Configuration 的遠端請求
        movieRepository.getConfiguration()
            .transform { result ->
                result.fold(
                    onSuccess = {
                        // API 成功，將設定資料寫入本地快取（DataStore）
                        userDataRepository.setConfiguration(it)
                        emit(Result.success(it))
                    },
                    onFailure = {
                        val config = userDataRepository.userData.firstOrNull()?.configuration
                        if (config != null) {
                            // 若有快取資料，則回傳 Success 狀態給 UI
                            emit(Result.success(config))
                        } else {
                            // 若沒有快取資料，則回傳原本的錯誤
                            emit(Result.failure(it))
                        }
                    },
                )
            }.flowOn(ioDispatcher)
}
