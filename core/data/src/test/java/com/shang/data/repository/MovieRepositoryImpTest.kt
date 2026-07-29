package com.shang.data.repository

import androidx.paging.testing.asSnapshot
import com.shang.common.NetworkException
import com.shang.database.dao.MovieCollectDao
import com.shang.database.dao.MovieHistoryDao
import com.shang.database.entity.MovieCollectEntity
import com.shang.database.entity.MovieHistoryEntity
import com.shang.model.ConfigurationBean
import com.shang.model.MovieCardResult
import com.shang.model.MovieCastAndCrewBean
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.model.MovieRecommendBean
import com.shang.model.MovieSearchBean
import com.shang.network.model.NetworkResponse
import com.shang.network.retrofit.MovieDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import strikt.assertions.isTrue

/**
 * MovieRepositoryImp 的單元測試
 *
 * 驗證資料協調邏輯：network 回應轉換為 Result、local DAO 資料轉換為 domain model
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("MovieRepositoryImp Tests")
class MovieRepositoryImpTest {

    private val movieDataSource = mockk<MovieDataSource>()
    private val movieCollectDao = mockk<MovieCollectDao>(relaxUnitFun = true)
    private val movieHistoryDao = mockk<MovieHistoryDao>(relaxUnitFun = true)
    private lateinit var repository: MovieRepositoryImp

    @BeforeEach
    fun setUp() {
        repository = MovieRepositoryImp(
            movieDataSource,
            movieCollectDao,
            movieHistoryDao,
            Dispatchers.Unconfined,
        )
    }

    private fun movie(id: Int) = MovieCardResult(id = id, title = "movie-$id")

    @Test
    @DisplayName("getConfiguration 成功時應回傳 Result.success")
    fun `getConfiguration should emit success when data source succeeds`() = runTest {
        // Given
        val config = ConfigurationBean(changeKeys = listOf("images"))
        coEvery { movieDataSource.getConfiguration() } returns NetworkResponse(code = 200, data = config)

        // When
        val result = repository.getConfiguration().first()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrNull()).isEqualTo(config)
    }

    @Test
    @DisplayName("getConfiguration 失敗時應回傳 Result.failure")
    fun `getConfiguration should emit failure when data source fails`() = runTest {
        // Given
        val error = NetworkException.HttpError(httpCode = 500)
        coEvery { movieDataSource.getConfiguration() } returns NetworkResponse(code = 500, error = error)

        // When
        val result = repository.getConfiguration().first()

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull()).isEqualTo(error)
    }

    @Test
    @DisplayName("getMovieGenres 成功時應回傳 Result.success")
    fun `getMovieGenres should emit success when data source succeeds`() = runTest {
        // Given
        val genres = MovieGenreBean(genres = listOf(MovieGenreBean.MovieGenre(id = 1, name = "Action")))
        coEvery { movieDataSource.getMovieGenres() } returns NetworkResponse(code = 200, data = genres)

        // When
        val result = repository.getMovieGenres().first()

        // Then
        expectThat(result.getOrNull()).isEqualTo(genres)
    }

    @Test
    @DisplayName("getMovieActor 成功時應回傳 Result.success")
    fun `getMovieActor should emit success when data source succeeds`() = runTest {
        // Given
        val castAndCrew = MovieCastAndCrewBean(id = 1)
        coEvery { movieDataSource.getMovieActor(1) } returns NetworkResponse(code = 200, data = castAndCrew)

        // When
        val result = repository.getMovieActor(1).first()

        // Then
        expectThat(result.getOrNull()).isEqualTo(castAndCrew)
    }

    @Test
    @DisplayName("getMovieListPager 應依 network 回應分頁載入電影清單")
    fun `getMovieListPager should load movies from data source`() = runTest {
        // Given
        val movies = listOf(movie(1), movie(2))
        coEvery { movieDataSource.getDiscoverMovie(withGenres = "28", page = 1) } returns
            NetworkResponse(code = 200, data = MovieListBean(page = 1, results = movies, totalPages = 1))

        // When
        val snapshot = repository.getMovieListPager("28").asSnapshot()

        // Then
        expectThat(snapshot.map { it.id }).containsExactly(1, 2)
    }

    @Test
    @DisplayName("getMovieSearchPager 應依 network 回應分頁載入搜尋結果")
    fun `getMovieSearchPager should load search results from data source`() = runTest {
        // Given
        val movies = listOf(movie(3))
        coEvery { movieDataSource.getMovieSearch(query = "query", page = 1) } returns
            NetworkResponse(code = 200, data = MovieSearchBean(page = 1, results = movies, totalPages = 1))

        // When
        val snapshot = repository.getMovieSearchPager("query").asSnapshot()

        // Then
        expectThat(snapshot.map { it.id }).containsExactly(3)
    }

    @Test
    @DisplayName("getMovieDetail 成功時應回傳 Result.success")
    fun `getMovieDetail should emit success when data source succeeds`() = runTest {
        // Given
        val detail = MovieDetailBean(id = 1, title = "movie-1")
        coEvery { movieDataSource.getMovieDetail(1) } returns NetworkResponse(code = 200, data = detail)

        // When
        val result = repository.getMovieDetail(1).first()

        // Then
        expectThat(result.getOrNull()).isEqualTo(detail)
    }

    @Test
    @DisplayName("getMovieRecommendations 失敗時應回傳 Result.failure")
    fun `getMovieRecommendations should emit failure when data source fails`() = runTest {
        // Given
        val error = NetworkException.HttpError(httpCode = 404)
        coEvery { movieDataSource.getMovieRecommendations(1) } returns
            NetworkResponse<MovieRecommendBean>(code = 404, error = error)

        // When
        val result = repository.getMovieRecommendations(1).first()

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull()).isEqualTo(error)
    }

    @Test
    @DisplayName("getCollectedMovieIds 應直接轉發 DAO 的收藏 id 清單")
    fun `getCollectedMovieIds should delegate to dao`() = runTest {
        // Given
        every { movieCollectDao.collectedMovieIds() } returns flowOf(listOf(1, 2, 3))

        // When
        val result = repository.getCollectedMovieIds().first()

        // Then
        expectThat(result).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    @DisplayName("getAllMovieCollect 應將 entity 轉換為已收藏的 domain model")
    fun `getAllMovieCollect should map entities to collected domain models`() = runTest {
        // Given
        val entity = MovieCollectEntity(id = 1, title = "movie-1", posterPath = "", voteAverage = 0.0, releaseDate = "", timestamp = 100L)
        every { movieCollectDao.getAllMovies() } returns flowOf(listOf(entity))

        // When
        val result = repository.getAllMovieCollect().first()

        // Then
        expectThat(result.single().id).isEqualTo(1)
        expectThat(result.single().isCollect).isTrue()
    }

    @Test
    @DisplayName("getMovieCollectEntityById 查無資料時應回傳 null")
    fun `getMovieCollectEntityById should return null when entity not found`() = runTest {
        // Given
        every { movieCollectDao.getMovieCollectEntityById(1) } returns flowOf(null)

        // When
        val result = repository.getMovieCollectEntityById(1).first()

        // Then
        expectThat(result).isNull()
    }

    @Test
    @DisplayName("insertMovieCollect 應將 domain model 轉換後寫入 DAO")
    fun `insertMovieCollect should insert mapped entity into dao`() = runTest {
        // Given
        val movie = MovieCardResult(id = 1, title = "movie-1")

        // When
        repository.insertMovieCollect(movie)

        // Then
        coVerify(exactly = 1) { movieCollectDao.insertMovieCollect(any()) }
    }

    @Test
    @DisplayName("deleteMovieCollect 應將 domain model 轉換後從 DAO 刪除")
    fun `deleteMovieCollect should delete mapped entity from dao`() = runTest {
        // Given
        val movie = MovieCardResult(id = 1, title = "movie-1")

        // When
        repository.deleteMovieCollect(movie)

        // Then
        coVerify(exactly = 1) { movieCollectDao.deleteMovie(any()) }
    }

    @Test
    @DisplayName("getAllMovieHistory 應將 entity 轉換為 domain model")
    fun `getAllMovieHistory should map entities to domain models`() = runTest {
        // Given
        val entity = MovieHistoryEntity(id = 2, title = "movie-2", posterPath = "", voteAverage = 0.0, releaseDate = "", timestamp = 200L)
        every { movieHistoryDao.getAllMovies() } returns flowOf(listOf(entity))

        // When
        val result = repository.getAllMovieHistory().first()

        // Then
        expectThat(result.single().id).isEqualTo(2)
        expectThat(result.single().isCollect).isFalse()
    }

    @Test
    @DisplayName("insertMovieHistory 應將 domain model 轉換後寫入 DAO")
    fun `insertMovieHistory should insert mapped entity into dao`() = runTest {
        // Given
        val movie = MovieCardResult(id = 3, title = "movie-3")

        // When
        repository.insertMovieHistory(movie)

        // Then
        coVerify(exactly = 1) { movieHistoryDao.insertMovie(any()) }
    }

    @Test
    @DisplayName("deleteMovieHistory 應將 domain model 轉換後從 DAO 刪除")
    fun `deleteMovieHistory should delete mapped entity from dao`() = runTest {
        // Given
        val movie = MovieCardResult(id = 3, title = "movie-3")

        // When
        repository.deleteMovieHistory(movie)

        // Then
        coVerify(exactly = 1) { movieHistoryDao.deleteMovie(any()) }
    }

    @Test
    @DisplayName("deleteAllMovieHistory 刪除筆數大於 0 時應回傳 true")
    fun `deleteAllMovieHistory should return true when rows deleted`() = runTest {
        // Given
        coEvery { movieHistoryDao.deleteAllMovies() } returns 3

        // When
        val result = repository.deleteAllMovieHistory()

        // Then
        expectThat(result).isTrue()
    }

    @Test
    @DisplayName("deleteAllMovieHistory 沒有刪除任何資料時應回傳 false")
    fun `deleteAllMovieHistory should return false when nothing deleted`() = runTest {
        // Given
        coEvery { movieHistoryDao.deleteAllMovies() } returns 0

        // When
        val result = repository.deleteAllMovieHistory()

        // Then
        expectThat(result).isFalse()
    }
}
