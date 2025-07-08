package com.shang.moviedetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MovieDetailScreen(movieId: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {
    Text("Movie Detail Screen : $movieId")
}
