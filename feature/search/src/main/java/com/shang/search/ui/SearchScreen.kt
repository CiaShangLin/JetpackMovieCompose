package com.shang.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shang.common.exception.toNetworkException
import com.shang.designsystem.component.JMLazyVerticalGrid
import com.shang.model.MovieCardResult
import com.shang.search.R
import com.shang.ui.ErrorScreen
import com.shang.ui.LoadingScreen
import com.shang.ui.MovieCard
import com.shang.ui.asMovieCardData
import kotlin.text.append
import kotlin.text.get

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    var inputText by remember { mutableStateOf("") }
    val movieSearchPager = viewModel.movieSearchPager.collectAsLazyPagingItems()
    val query by viewModel.searchQueryFlow.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = inputText,
            onValueChange = {
                inputText = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    viewModel.startSearch(inputText)
                },
            ),
            label = {
                Text(stringResource(id = R.string.search_movie_hint))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(id = R.string.search_movie_hint),
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            shape = MaterialTheme.shapes.large,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                // 設定容器背景顏色
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,

                // 移除底部指示線
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
        Divider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            thickness = 1.dp,
        )

        movieSearchPager.loadState.refresh
        movieSearchPager.loadState.append
        movieSearchPager.loadState.prepend
        movieSearchPager.loadState.source
        when {
            query.isEmpty() -> {
                NotSearchScreen()
            }
            movieSearchPager.loadState.refresh is LoadState.Loading -> {
                SearchLoadingScreen()
            }
            movieSearchPager.loadState.refresh is LoadState.Error -> {
                val state = movieSearchPager.loadState.refresh as LoadState.Error
                SearchErrorScreen(
                    onRetry = { viewModel.retrySearch() },
                    networkException = state.error.toNetworkException(),
                )
            }
            else -> {
                SearchResultScreen(
                    movieSearchPager = movieSearchPager,
                    isLoadingMore = movieSearchPager.loadState.append is LoadState.Loading,
                    isError = movieSearchPager.loadState.append is LoadState.Error,
                    isEndOfData = movieSearchPager.loadState.append.endOfPaginationReached,
                )
            }
        }
    }
}

@Composable
fun NotSearchScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(id = R.string.search_movie_hint),
            modifier = Modifier.padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(id = R.string.search_movie_start),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(id = R.string.search_movie_input_hint),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun SearchErrorScreen(onRetry: () -> Unit, networkException: Exception?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ErrorScreen(
            onRetry = onRetry,
            networkException = networkException,
        )
    }
}

@Composable
fun SearchLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingScreen()
    }
}

@Composable
fun SearchResultScreen(
    movieSearchPager: LazyPagingItems<MovieCardResult>,
    isLoadingMore: Boolean,
    isError: Boolean,
    isEndOfData: Boolean,
) {
    JMLazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(movieSearchPager.itemCount) {
            val movie = movieSearchPager[it] ?: return@items
            MovieCard(
                modifier = Modifier,
                data = movie.asMovieCardData(),
            )
        }

        // 底部載入狀態
        item {
            when {
                isLoadingMore -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingScreen()
                    }
                }
                isError -> {
                    ErrorScreen(
                        onRetry = { movieSearchPager.retry() },
                        networkException = null,
                    )
                }
                isEndOfData -> {
                    Text(
                        text = "沒有更多資料",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
