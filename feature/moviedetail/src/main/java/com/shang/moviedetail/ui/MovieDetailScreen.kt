package com.shang.moviedetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.designsystem.component.JMAsyncImage
import com.shang.model.MovieDetailBean

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

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = movieDetail.value) {
            is MovieDetailUiState.Loading -> MovieDetailLoadingScreen()
            is MovieDetailUiState.Success -> MovieDetailSuccessScreen(state.data)
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
                    (movieDetail.value as? MovieDetailUiState.Success)?.data?.let {
                        viewModel.toggleCollect(it)
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
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Text(
            text = "發生錯誤：$message",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Composable
private fun MovieDetailSuccessScreen(
    data: MovieDetailBean,
) {
    JMAsyncImage(
        model = data.backdropPath,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(12.dp)),
    )
}

@Preview
@Composable
private fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieId = 12345) {}
}
