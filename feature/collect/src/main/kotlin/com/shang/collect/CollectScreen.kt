package com.shang.collect

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.model.MovieListBean
import com.shang.ui.MovieCard

@Composable
fun CollectScreen(viewModel: CollectViewModel = hiltViewModel()) {
    val allMovieCollect by viewModel.allMovieCollect.collectAsStateWithLifecycle()
    Text("Collect Screen : $allMovieCollect", style = TextStyle(color = MaterialTheme.colorScheme.error))

    when (allMovieCollect) {
        is CollectUiState.Error -> TODO()
        CollectUiState.Loading -> TODO()
        is CollectUiState.Success -> TODO()
    }
}

@Composable
fun CollectLoadingScreen() {
    Text("Loading...", style = TextStyle(color = MaterialTheme.colorScheme.primary))
}

@Composable
fun CollectErrorScreen(message: String) {
    Text("Error: $message", style = TextStyle(color = MaterialTheme.colorScheme.error))
}

@Composable
fun CollectSuccessScreen(movieCollectList: List<MovieListBean.Result>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(movieCollectList) { movie ->
                MovieCard(
                    data = movie,

                )
            }
        },
    )
}
