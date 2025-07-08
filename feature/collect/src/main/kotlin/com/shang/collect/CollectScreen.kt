package com.shang.collect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.designsystem.component.JMLazyVerticalGrid
import com.shang.model.MovieCardResult
import com.shang.ui.MovieCard
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardData

@Composable
fun CollectScreen(viewModel: CollectViewModel = hiltViewModel()) {
    val allMovieCollect by viewModel.allMovieCollect.collectAsStateWithLifecycle()

    when (val result = allMovieCollect) {
        is CollectUiState.Error -> CollectErrorScreen(message = "")
        CollectUiState.Loading -> CollectLoadingScreen()
        is CollectUiState.Success -> CollectSuccessScreen(
            result.movieCollectList,
            onCollectClick = { movie ->
                viewModel.toggleMovieCollectStatus(movie)
            },
        )
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
fun CollectSuccessScreen(
    movieCollectList: List<MovieCardResult>,
    onCollectClick: (MovieCardData) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.collect_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        Divider(
            color = MaterialTheme.colorScheme.onSurface,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        )
        JMLazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(), // 外部間距
            contentPadding = PaddingValues(8.dp), // 外部間距
            verticalArrangement = Arrangement.spacedBy(8.dp), // 垂直間距
            horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平間距
        ) {
            items(movieCollectList) { movie ->
                MovieCard(
                    data = movie.asMovieCardData(),
                    onCollectClick = onCollectClick,
                )
            }
        }
    }
}
