package com.shang.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.designsystem.component.JMLazyVerticalGrid
import com.shang.history.R
import com.shang.model.MovieCardResult
import com.shang.ui.MovieCard
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardData

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    val allMovieHistory = viewModel.allMovieHistory.collectAsStateWithLifecycle()

    if (allMovieHistory.value.isEmpty()) {
        NotDataScreen()
    } else {
        HistoryMovieScreen(
            data = allMovieHistory.value,
            onCollectClick = {
                viewModel.toggleMovieCollectStatus(it)
            },
            onClearClick = {
                viewModel.clearAllMovieHistory()
            },
        )
    }
}

@Composable
private fun NotDataScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.icon_empty),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(id = R.string.history_empty),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun HistoryMovieScreen(
    data: List<MovieCardResult>,
    onCollectClick: (MovieCardData) -> Unit,
    onClearClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HistoryTitle(onClearClick = onClearClick)
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
            items(data) { movie ->
                MovieCard(
                    data = movie.asMovieCardData(),
                    onCollectClick = {
                        onCollectClick.invoke(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun HistoryTitle(onClearClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.history_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier,
        )
        Row(
            modifier = Modifier.clickable {
                onClearClick.invoke()
            },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.history_clear),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )
        }
    }
}

@Preview
@Composable
fun HistoryMovieScreenPreview() {
    HistoryMovieScreen(listOf(), onCollectClick = {}, onClearClick = {})
}
