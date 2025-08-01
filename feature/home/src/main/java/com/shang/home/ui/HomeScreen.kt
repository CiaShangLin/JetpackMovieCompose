package com.shang.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.shang.designsystem.component.JMLazyVerticalGrid
import com.shang.designsystem.component.JMScrollableTabRow
import com.shang.designsystem.component.JMTab
import com.shang.model.MovieGenreBean
import com.shang.ui.ErrorScreen
import com.shang.ui.LoadingScreen
import com.shang.ui.MovieCard
import com.shang.ui.asMovieCardData

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
) {
    val movieGenresState by viewModel.movieGenres.collectAsState()

    when (val state = movieGenresState) {
        is HomeUiState.Loading -> HomeLoadingScreen()
        is HomeUiState.Success -> HomeSuccessScreen(state.movieGenres, onMovieClick = onMovieClick)
        is HomeUiState.Error -> HomeErrorScreen(onRetry = {
            viewModel.retry()
        })
    }
}

@Composable
fun HomeLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingScreen()
    }
}

@Composable
fun HomeErrorScreen(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ErrorScreen(
            onRetry = onRetry,
        )
    }
}

@Composable
fun HomeSuccessScreen(movieGenres: MovieGenreBean, onMovieClick: (Int) -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = { movieGenres.genres.size },
    )

    LaunchedEffect(selectedTabIndex) {
        pageState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pageState.currentPage) {
        selectedTabIndex = pageState.currentPage
    }

    Column(modifier = Modifier.fillMaxSize()) {
        JMScrollableTabRow(
            selectedIndex = selectedTabIndex,
            onTabSelected = { index -> selectedTabIndex = index },
        ) {
            movieGenres.genres.forEachIndexed { index, genre ->
                JMTab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = genre.name,
                    modifier = Modifier.fillMaxHeight(),
                )
            }
        }
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            HomeScreenPager(
                page = page,
                genre = movieGenres.genres[page],
                onMovieClick = onMovieClick,
            )
        }
    }
}

@Composable
fun HomeScreenPager(
    page: Int,
    genre: MovieGenreBean.MovieGenre,
    onMovieClick: (Int) -> Unit,
    viewModel: HomeContentViewModel = hiltViewModel<HomeContentViewModel, HomeContentViewModel.Factory>(
        key = "HomeContentViewModel_${page}_${genre.id}",
        creationCallback = { factory -> factory.create(genre) },
    ),
) {
    val movieList = viewModel.movieList.collectAsLazyPagingItems()

    JMLazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp), // 外部間距
        verticalArrangement = Arrangement.spacedBy(8.dp), // 垂直間距
        horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平間距
    ) {
        items(movieList.itemCount) {
            val movieCardData = movieList[it]?.asMovieCardData() ?: return@items
            MovieCard(
                modifier = Modifier,
                data = movieCardData,
                onMovieClick = {
                    onMovieClick(it.movieCardId)
                },
                onCollectClick = { movie ->
                    viewModel.toggleMovieCollectStatus(movie)
                },
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val mockGenres = MovieGenreBean(
        genres = listOf(
            MovieGenreBean.MovieGenre(id = 1, name = "Action"),
            MovieGenreBean.MovieGenre(id = 2, name = "Comedy"),
            MovieGenreBean.MovieGenre(id = 3, name = "Drama"),
        ),
    )
    HomeSuccessScreen(mockGenres) {
    }
}
