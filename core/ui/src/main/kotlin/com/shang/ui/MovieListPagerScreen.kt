package com.shang.ui

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> MovieListPagerScreen(pager: LazyPagingItems<T>, content: @Composable () -> Unit) {
    when {
        pager.loadState.refresh is LoadState.NotLoading -> LoadingScreen()
        pager.loadState.refresh is LoadState.Loading -> LoadingScreen()
        pager.loadState.refresh is LoadState.Error -> ErrorScreen()
        pager.loadState.append is LoadState.Loading -> LoadingScreen()
        pager.loadState.append is LoadState.Error -> ErrorScreen()
        pager.loadState.prepend is LoadState.Error -> ErrorScreen()
        pager.loadState.prepend is LoadState.Loading -> LoadingScreen()
        else -> {
            content()
        }
    }
}