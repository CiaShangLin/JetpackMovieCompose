package com.shang.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.common.UiState

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val configurationState = viewModel.configuration.collectAsStateWithLifecycle()
    val userData = viewModel.userData.collectAsStateWithLifecycle()
    val movie by viewModel.discoverMovie.collectAsStateWithLifecycle()

    Column {
        Text("Home Scree", style = TextStyle(color = MaterialTheme.colorScheme.error))

        when (val state = configurationState.value) {
            is UiState.Loading -> Text("Loading...", style = TextStyle(color = MaterialTheme.colorScheme.primary))
            is UiState.Success -> Text("Home Screen : ${state.data}", style = TextStyle(color = MaterialTheme.colorScheme.onSurface))
            is UiState.Error -> Text("Error: ${state.error}", style = TextStyle(color = MaterialTheme.colorScheme.error))
        }

        Text("----------", style = TextStyle(color = MaterialTheme.colorScheme.error))

        Text("Discover Movie: $movie", style = TextStyle(color = MaterialTheme.colorScheme.primary))
        // Text("userData:$userData", style = TextStyle(color = MaterialTheme.colorScheme.error))
    }
}
