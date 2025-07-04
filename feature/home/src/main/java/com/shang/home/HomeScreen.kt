package com.shang.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.designsystem.component.JMScrollableTabRow
import com.shang.designsystem.component.JMTab
import com.shang.home.ui.HomeUiState
import com.shang.model.MovieGenreBean
import kotlin.text.get

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val movieGenresState by viewModel.movieGenres.collectAsStateWithLifecycle()

    when (val state = movieGenresState) {
        is HomeUiState.Loading -> HomeLoadingScreen()
        is HomeUiState.Success -> HomeContent(state.movieGenres)
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
fun HomeContent(movieGenres: MovieGenreBean) {
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Content for ${movieGenres.genres[page].name} $page")
            }
        }
    }
}
