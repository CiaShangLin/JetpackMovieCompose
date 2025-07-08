package com.shang.domain.usecase

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieDetailBean
import com.shang.model.asMovieCardResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(movieId: Int): Flow<Result<MovieDetailBean>> {
        return movieRepository.getMovieDetail(movieId)
            .onEach { result ->
                if (result.isSuccess) {
                    result.getOrNull()?.let {
                        runCatching {
                            // 拿到Api資料時寫入歷史紀錄
                            movieRepository.insertMovieHistory(it.asMovieCardResult())
                        }
                    }
                }
            }.flowOn(ioDispatcher)
    }
}
