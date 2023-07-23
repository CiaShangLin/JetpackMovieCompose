package com.shang.jetpackmoviecompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun FavoritesPage(homeViewModel: HomeViewModel = viewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favorites")
    }

}