package com.shang.jetpackmoviecompose.ui.favorites

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shang.jetpackmoviecompose.MovieViewHolder
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import com.shang.jetpackmoviecompose.ui.favorites.FavoritesViewModel
import com.shang.jetpackmoviecompose.ui.home.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesPage(favoritesViewModel: FavoritesViewModel = hiltViewModel()) {


    val data by favoritesViewModel.getAllMovieFavor().collectAsState(initial = null)

    val refresh by remember {
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
            items(count = data?.size ?: 0) {

            }
        }

        PullRefreshIndicator(
            refreshing = refresh,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}