package com.shang.domain.usecase

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetHistoryMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<MovieCardResult>> {
        val allMovieHistory = movieRepository.getAllMovieHistory()
        val collectIds = movieRepository.getCollectedMovieIds()
        return combine(allMovieHistory, collectIds) { history, collectIds ->
            history.map { movie ->
                movie.copy(
                    isCollect = collectIds.contains(movie.id),
                )
            }
        }.flowOn(ioDispatcher)
    }
}
