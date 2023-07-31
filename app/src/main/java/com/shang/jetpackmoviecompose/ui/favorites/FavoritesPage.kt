package com.shang.jetpackmoviecompose.ui.favorites

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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


    val data by favoritesViewModel.getAllMovieFavor().collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize()) {

        Button(
            onClick = {


            },
        ) {
            Text(text = "Add")
        }
        Button(onClick = {

        }) {
            Text(text = "Delete")
        }
        Text(text = "${data.toString()}")
    }
}