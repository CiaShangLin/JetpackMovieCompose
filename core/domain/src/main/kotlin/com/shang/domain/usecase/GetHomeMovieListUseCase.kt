package com.shang.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieListBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 取得首頁電影清單 UseCase
 *
 * 功能特色：
 * - 收藏狀態異動時自動刷新 UI
 * - 使用 Set 結構提升收藏狀態查詢效率
 * - 完整錯誤處理機制
 * - 符合 Clean Architecture 原則
 */
class GetHomeMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @Dispatcher(CommonDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * 取得帶有即時收藏狀態的電影分頁資料
     *
     * @param withGenres 指定要查詢的電影類型
     * @return Flow<PagingData<MovieListBean.Result>> 每筆資料帶有即時收藏狀態
     *
     * 效能優化：
     * - 使用 Set 進行 O(1) 查詢效率
     * - 只在 IO 執行緒執行耗時操作
     * - 收藏狀態變動時自動觸發 UI 刷新
     */
    operator fun invoke(withGenres: String): Flow<PagingData<MovieListBean.Result>> {
        // 取得分頁資料 Flow
        val pagerFlow = movieRepository.getMovieGenrePager(withGenres)
            .flowOn(ioDispatcher)

        // 取得收藏電影 id 集合，轉為 Set 提升查詢效率
        val collectIdsFlow = movieRepository.collectedMovieIds()
            .map { it.toSet() } // 轉為 Set，contains 查詢從 O(n) 優化為 O(1)
            .flowOn(ioDispatcher)

        // combine 兩個 Flow，確保收藏狀態異動時自動刷新
        return pagerFlow
            .combine(collectIdsFlow) { pagingData, collectIds ->
                pagingData.map { movie ->
                    // 直接使用 movie.id，移除不必要的 toString() 轉換
                    movie.copy(isCollect = collectIds.contains(movie.id))
                }
            }
    }
}
