package com.shang.domain.usecase

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

/**
 * GetMovieSearchUseCase 的單元測試
 *
 * 驗證搜尋分頁資料流與收藏狀態 combine 後的結果是否正確
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("GetMovieSearchUseCase Tests")
class GetMovieSearchUseCaseTest {

    private val movieRepository = mockk<MovieRepository>()
    private lateinit var useCase: GetMovieSearchUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetMovieSearchUseCase(movieRepository, Dispatchers.Unconfined)
    }

    private fun movie(id: Int) = MovieCardResult(id = id, title = "movie-$id")

    // 標記為完全載入完成，避免 asSnapshot 誤判仍有分頁需要載入而永遠等待
    private val fullyLoadedStates = LoadStates(
        refresh = LoadState.NotLoading(endOfPaginationReached = true),
        prepend = LoadState.NotLoading(endOfPaginationReached = true),
        append = LoadState.NotLoading(endOfPaginationReached = true),
    )

    private fun <T : Any> pagingDataOf(items: List<T>) = PagingData.from(items, fullyLoadedStates)

    @Test
    @DisplayName("搜尋結果應依已收藏電影 id 清單標記 isCollect")
    fun `should mark isCollect according to collected movie ids`() = runTest {
        // Given
        val searchResults = listOf(movie(1), movie(2), movie(3))
        every { movieRepository.getMovieSearchPager("query") } returns
            flowOf(pagingDataOf(searchResults))
        every { movieRepository.getCollectedMovieIds() } returns flowOf(listOf(2))

        // When
        val snapshot = useCase("query", backgroundScope).asSnapshot()

        // Then
        expectThat(snapshot.map { it.id to it.isCollect })
            .containsExactly(1 to false, 2 to true, 3 to false)
    }

    @Test
    @DisplayName("收藏狀態變動時應更新對應項目，且不需重新查詢搜尋分頁來源")
    fun `should update collect status without re-querying the search pager`() = runTest {
        // Given
        val searchResults = listOf(movie(1), movie(2))
        every { movieRepository.getMovieSearchPager("query") } returns
            flowOf(pagingDataOf(searchResults))
        every { movieRepository.getCollectedMovieIds() } returns
            flowOf(emptyList(), listOf(1))

        // When
        val snapshot = useCase("query", backgroundScope).asSnapshot()

        // Then：最終快照反映最新收藏狀態
        expectThat(snapshot.map { it.id to it.isCollect })
            .containsExactly(1 to true, 2 to false)
        // 且 collectIdsFlow 多次 emit 期間，搜尋分頁來源只被查詢一次（cachedIn 在 combine 之前，不會被收藏狀態變動觸發重新查詢）
        verify(exactly = 1) { movieRepository.getMovieSearchPager("query") }
    }
}
