package com.shang.moviedetail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.common.UiState
import com.shang.designsystem.component.JMAsyncImage
import com.shang.designsystem.theme.StarRatingColor
import com.shang.model.MovieCardResult
import com.shang.model.MovieDetailBean
import com.shang.model.asMovieCardResult
import com.shang.moviedetail.R
import com.shang.ui.MovieCard
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardData

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel<MovieDetailViewModel, MovieDetailViewModel.Factory>(
        key = "MovieDetailViewModel_$movieId",
        creationCallback = { factory -> factory.create(movieId) },
    ),
    onBackClick: () -> Unit,
) {
    val movieDetail = viewModel.movieDetail.collectAsStateWithLifecycle()
    val movieCollect = viewModel.movieCollect.collectAsStateWithLifecycle()
    val movieRecommend = viewModel.movieRecommendations.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = movieDetail.value) {
            is MovieDetailUiState.Loading -> MovieDetailLoadingScreen()
            is MovieDetailUiState.Success -> MovieDetailSuccessScreen(
                data = state.data,
                movieRecommend = movieRecommend.value,
                onMovieClick = { movieCardData ->
                },
                onCollectClick = { movieCardData ->
                    viewModel.toggleCollect(movieCardData, movieCardData.movieCardIsCollect)
                },
            )

            is MovieDetailUiState.Error -> MovieDetailErrorScreen(state.message)
        }

        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 8.dp)
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small,
                )
                .clickable {
                    onBackClick()
                },
        )
        CollectButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 8.dp)
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small,
                )
                .clickable {
                    (movieDetail.value as? MovieDetailUiState.Success)?.data?.asMovieCardResult()?.asMovieCardData()?.let {
                        viewModel.toggleCollect(it, movieCollect.value)
                    }
                },
            isCollect = movieCollect.value,
        )
    }
}

/**
 * 返回按鈕
 */
@Composable
private fun BackButton(
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            modifier = Modifier,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

/**
 * 收藏按鈕
 */
@Composable
private fun CollectButton(
    modifier: Modifier,
    isCollect: Boolean,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = if (isCollect) {
                Icons.Rounded.Favorite
            } else {
                Icons.Rounded.FavoriteBorder
            },
            contentDescription = "Collect",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun MovieDetailLoadingScreen() {
    Text("Loading")
}

@Composable
private fun MovieDetailErrorScreen(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "發生錯誤：$message",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

/**
 * 電影詳情成功頁面
 */
@Composable
private fun MovieDetailSuccessScreen(
    data: MovieDetailBean,
    movieRecommend: UiState<List<MovieCardResult>>,
    onMovieClick: (data: MovieCardData) -> Unit,
    onCollectClick: (data: MovieCardData) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        JMAsyncImage(
            model = data.backdropPath,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
        )
        LazyColumn {
            item(key = data.title) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                    ),
                )
            }
            item {
                Row(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                    MovieStarRating(rating = data.voteAverage)
                    Spacer(Modifier.width(16.dp))
                    MovieReleaseDate(releaseDate = data.releaseDate)
                    Spacer(Modifier.width(16.dp))
                    MovieRuntime(runtime = data.runtime)
                }
            }
            item {
                MovieInfo(data.overview)
            }
            item {
                MovieActorList()
            }
            item {
                MovieGuessLikeList(
                    modifier = Modifier,
                    movieRecommend = movieRecommend,
                    onMovieClick = onMovieClick,
                    onCollectClick = onCollectClick,
                )
            }
        }
    }
}

/**
 * 電影星級評分
 */
@SuppressLint("DefaultLocale")
@Composable
private fun MovieStarRating(
    rating: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Rounded.StarRate,
            contentDescription = "Rating",
            modifier = Modifier.size(16.dp),
            tint = StarRatingColor,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = String.format("%.2f", rating),
            modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

/**
 *  電影上映日期
 */
@Composable
private fun MovieReleaseDate(
    releaseDate: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.CalendarMonth,
            contentDescription = "CalendarMonth",
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = releaseDate,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

/**
 * 電影時長
 */
@Composable
private fun MovieRuntime(
    runtime: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Timer,
            contentDescription = "CalendarMonth",
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = runtime.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

/**
 * 電影簡介
 */
@Composable
private fun MovieInfo(overview: String) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) {
        Text(
            text = stringResource(R.string.movie_plot_synopsis),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

/**
 * 電影演員列表
 */
@Composable
private fun MovieActorList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
    ) {
        Text(
            text = stringResource(R.string.movie_main_cast),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier,
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {}
    }
}

/**
 * 猜你喜歡的電影列表
 */
@Composable
private fun MovieGuessLikeList(
    modifier: Modifier = Modifier,
    movieRecommend: UiState<List<MovieCardResult>>,
    onMovieClick: (data: MovieCardData) -> Unit,
    onCollectClick: (data: MovieCardData) -> Unit,
) {
    when (movieRecommend) {
        is UiState.Loading -> {
            Text("Loading recommendations...")
        }

        is UiState.Error -> {
        }

        is UiState.Success<List<MovieCardResult>> -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Text(
                    text = stringResource(R.string.movie_recommendations),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier,
                )
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(movieRecommend.data) { movieCardResult ->
                        MovieCard(
                            modifier = Modifier
                                .fillParentMaxWidth(
                                    fraction = 0.5f,
                                )
                                .padding(horizontal = 8.dp),
                            data = movieCardResult.asMovieCardData(),
                            onMovieClick = {
                                onMovieClick.invoke(it)
                            },
                            onCollectClick = {
                                onCollectClick.invoke(it)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieId = 12345) {}
}
