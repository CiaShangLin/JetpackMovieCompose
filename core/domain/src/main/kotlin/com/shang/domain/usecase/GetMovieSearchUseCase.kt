package com.shang.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 取得搜尋電影清單 UseCase
 *
 * 1. 從 [MovieRepository] 取得搜尋關鍵字對應的電影分頁資料流
 * 2. 取得已收藏電影 id 清單，並標記每部電影的收藏狀態
 * 3. 支援依賴注入與 IO Dispatcher 切換，確保效能與可測試性
 *
 * @param movieRepository 電影資料來源 Repository
 * @param ioDispatcher 執行 IO 操作的協程 Dispatcher
 *
 * @constructor 由 DI 框架注入依賴
 */
class GetMovieSearchUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * 取得搜尋電影分頁資料流，並標記每部電影是否已收藏
     *
     * @param query 搜尋關鍵字
     * @param scope 提供 cachedIn 的 CoroutineScope，交由呼叫端決定生命週期
     * @return Flow<PagingData<MovieCardResult>> 分頁資料流，已標記 isCollect 狀態
     */
    operator fun invoke(query: String, scope: CoroutineScope): Flow<PagingData<MovieCardResult>> {
        val pagerFlow = movieRepository.getMovieSearchPager(query)
            .flowOn(ioDispatcher)
            .cachedIn(scope)

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
