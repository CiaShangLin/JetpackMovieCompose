package com.shang.domain.usecase

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieRecommendUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(movieId: Int): Flow<Result<List<MovieCardResult>>> {
        val collectIdsFlow = movieRepository.getCollectedMovieIds()
            .map { it.toSet() }
            .flowOn(ioDispatcher)
        val movieRecommendationsFlow = movieRepository.getMovieRecommendations(movieId)
            .map {
                it.fold(
                    onSuccess = { Result.success(it.results) },
                    onFailure = { Result.failure(it) },
                )
            }
            .flowOn(ioDispatcher)

        return movieRecommendationsFlow.combine(collectIdsFlow) { recommendations, collectIds ->
            recommendations.mapCatching {
                it.map {
                    it.copy(isCollect = collectIds.contains(it.id))
                }
            }
        }.flowOn(ioDispatcher)
    }
}
