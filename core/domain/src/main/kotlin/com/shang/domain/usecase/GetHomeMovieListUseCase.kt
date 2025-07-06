package com.shang.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieListBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 取得首頁電影清單 UseCase
 *
 * 1. 從 [MovieRepository] 取得指定類型的電影分頁資料流
 * 2. 取得已收藏電影 id 清單，並標記每部電影的收藏狀態
 * 3. 支援依賴注入與 IO Dispatcher 切換，確保效能與可測試性
 *
 * @param movieRepository 電影資料來源 Repository
 * @param ioDispatcher 執行 IO 操作的協程 Dispatcher
 *
 * @constructor 由 DI 框架注入依賴
 */
class GetHomeMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * 取得首頁電影分頁資料流，並標記每部電影是否已收藏
     *
     * @param withGenres 指定查詢的電影類型 id 字串
     * @param viewModelScope 提供 cachedIn 的 CoroutineScope，交由呼叫端決定生命週期
     * @return Flow<PagingData<MovieListBean.Result>> 分頁資料流，已標記 isCollect 狀態
     */
    operator fun invoke(withGenres: String, viewModelScope: CoroutineScope): Flow<PagingData<MovieListBean.Result>> {
        // 不需要 cachedIn，讓調用方決定
        val pagerFlow = movieRepository.getMovieGenrePager(withGenres)
            .flowOn(ioDispatcher)
            .cachedIn(viewModelScope)

        // 取得已收藏電影 id 清單，轉為 Set 以提升 contains 查詢效能
        val collectIdsFlow = movieRepository.getCollectedMovieIds()
            .map { it.toSet() }
            .flowOn(ioDispatcher)

        // 合併分頁資料與收藏 id，標記每部電影的 isCollect 狀態
        return pagerFlow
            .combine(collectIdsFlow) { pagingData, collectIds ->
                pagingData.map { movie ->
                    movie.copy(isCollect = collectIds.contains(movie.id))
                }
            }
    }
}
