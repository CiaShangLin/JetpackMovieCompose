package com.shang.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shang.designsystem.component.JMScrollableTabRow
import com.shang.designsystem.component.JMTab
import com.shang.model.MovieGenreBean

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val movieGenresState by viewModel.movieGenres.collectAsStateWithLifecycle()

    when (val state = movieGenresState) {
        is HomeUiState.Loading -> HomeLoadingScreen()
        is HomeUiState.Success -> HomeSuccessScreen(state.movieGenres)
        is HomeUiState.Error -> HomeErrorScreen()
    }
}

@Composable
fun HomeLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loading...")
    }
}

@Composable
fun HomeErrorScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Error loading data")
    }
}

@Composable
fun HomeSuccessScreen(movieGenres: MovieGenreBean) {
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
            HomeScreenPager(page = page, movieGenres.genres[page])
        }
    }
}

@Composable
fun HomeScreenPager(
    page: Int,
    genre: MovieGenreBean.MovieGenre,
    viewModel: HomeContentViewModel = hiltViewModel(),
) {
    val id by viewModel.movieGenres.collectAsState()
    LaunchedEffect(genre) {
        viewModel.setMovieGenre(genre)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        // Text("Content for  $page")
        Text("Content for  $page $id")
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
    HomeSuccessScreen(mockGenres)
}
