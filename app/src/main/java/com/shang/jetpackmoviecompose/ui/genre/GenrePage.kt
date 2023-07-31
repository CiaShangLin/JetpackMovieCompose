package com.shang.jetpackmoviecompose.ui.genre

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.MovieViewHolder
import com.shang.jetpackmoviecompose.di.NetworkModule
import javax.inject.Inject

@Composable
fun GenrePage(genre: MovieGenreBean.Genre, viewModel: GenreViewModel = hiltViewModel()) {

    viewModel.setGenre(genre)
    val data = viewModel.getMovieGenreDetail().collectAsLazyPagingItems()

    return Surface(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 70.dp)
        ) {
            items(count = data.itemCount, key = {
                data[it]!!.getMovieID()
            }) {
                MovieViewHolder(data = data[it]!!)
            }
        }
    }
}