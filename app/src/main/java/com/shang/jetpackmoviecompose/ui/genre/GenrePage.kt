package com.shang.jetpackmoviecompose.ui.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.MovieViewHolder
import com.shang.jetpackmoviecompose.di.NetworkModule
import javax.inject.Inject

@Composable
fun GenrePage(genre: MovieGenreBean.Genre, viewModel: GenreViewModel = hiltViewModel()) {
    viewModel.setGenre(genre)

    val data by viewModel.genreSharedFlow.collectAsState(initial = null)

    return Surface(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 70.dp)
        ) {
            items(count = data?.results?.size ?: 0) {
                MovieViewHolder(data = data!!.results.get(it))
            }
        }
    }
}