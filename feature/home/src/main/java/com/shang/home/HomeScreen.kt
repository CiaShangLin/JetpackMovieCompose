package com.shang.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.common.UiState
import com.shang.model.ConfigurationBean

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val configurationState = viewModel.configuration.collectAsStateWithLifecycle()
    val userData = viewModel.userData.collectAsStateWithLifecycle()

    Column {
        Text("Home Scree", style = TextStyle(color = MaterialTheme.colorScheme.error))

        when (val state = configurationState.value) {
            is UiState.Loading -> Text("Loading...", style = TextStyle(color = MaterialTheme.colorScheme.primary))
            is UiState.Success -> Text("Home Screen : ${state.data}", style = TextStyle(color = MaterialTheme.colorScheme.onSurface))
            is UiState.Error -> Text("Error: ${state.error}", style = TextStyle(color = MaterialTheme.colorScheme.error))
        }

        Text("----------", style = TextStyle(color = MaterialTheme.colorScheme.error))

        Text("userData:$userData", style = TextStyle(color = MaterialTheme.colorScheme.error))

        Button(onClick = {
            viewModel.setConfiguration((configurationState.value as UiState.Success<ConfigurationBean>).data)
        }) {
            Text("Click Me", style = TextStyle(color = MaterialTheme.colorScheme.onPrimary))
        }
    }
}
