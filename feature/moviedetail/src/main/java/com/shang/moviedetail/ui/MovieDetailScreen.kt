package com.shang.moviedetail.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.model.MovieDetailBean

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel<MovieDetailViewModel, MovieDetailViewModel.Factory>(
        key = "MovieDetailViewModel_$movieId",
        creationCallback = { factory ->
            factory.create(movieId)
        },
    ),
) {
    val movieDetail = viewModel.movieDetail.collectAsStateWithLifecycle()

    when (val state = movieDetail.value) {
        is MovieDetailUiState.Loading -> MovieDetailLoadingScreen()
        is MovieDetailUiState.Success -> MovieDetailSuccessScreen(state.data)
        is MovieDetailUiState.Error -> MovieDetailErrorScreen(state.message)
    }
}

@Composable
fun MovieDetailLoadingScreen() {
    Text("Loading movie details...")
}

@Composable
fun MovieDetailErrorScreen(message: String) {
    Text("Error loading movie details: $message")
}

@Composable
fun MovieDetailSuccessScreen(data: MovieDetailBean) {
    // Display movie details here
    Text("Movie Title: ${data.title}")
    // Add more details as needed
}
