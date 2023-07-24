package com.shang.jetpackmoviecompose.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shang.jetpackmoviecompose.room.entity.MovieFavorEntity
import com.shang.jetpackmoviecompose.ui.favorites.FavoritesViewModel
import com.shang.jetpackmoviecompose.ui.home.HomeViewModel

@Composable
fun FavoritesPage(favoritesViewModel: FavoritesViewModel = hiltViewModel()) {


    val data by favoritesViewModel.getAllMovieFavor().observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                favoritesViewModel.insertMovieFavor(
                    MovieFavorEntity(
                        id = 1,
                        title = "Movie Title",
                        poster_path = "poster_path.jpg",
                        vote_average = 7.5,
                        release_date = "2023-07-24",
                        timestamp = System.currentTimeMillis().toInt()
                    )
                )
            },
        ) {
            Text(text = "Add")
        }
        Button(onClick = {
            favoritesViewModel.deleteMovieFavor(
                MovieFavorEntity(
                    id = 1,
                    title = "Movie Title",
                    poster_path = "poster_path.jpg",
                    vote_average = 7.5,
                    release_date = "2023-07-24",
                    timestamp = System.currentTimeMillis().toInt()
                )
            )
        }) {
            Text(text = "Delete")
        }
        Text(text = "${data.toString()}")
    }
}