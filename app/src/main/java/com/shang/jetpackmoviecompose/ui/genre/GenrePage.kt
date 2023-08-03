package com.shang.jetpackmoviecompose.ui.genre

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.MovieViewHolder
import com.shang.jetpackmoviecompose.ui.favorites.FavoritesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenrePage(
    genre: MovieGenreBean.Genre,
    viewModel: GenreViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {

    viewModel.setGenre(genre)
    val data = viewModel.getMovieGenreDetail().collectAsLazyPagingItems()
    var refresh by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = refresh, onRefresh = {
        Log.d("DEBUG", "refresh")
        //refresh=true
    })

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 70.dp)
        ) {
            items(count = data.itemCount) {
                MovieViewHolder(data = data[it]!!, favoritesRepository = favoritesViewModel)
            }
        }

        PullRefreshIndicator(
            refreshing = refresh,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}