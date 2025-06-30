package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shang.jetpackmoviecompose.theme.JetpackMovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackMovieComposeTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.data.collectAsStateWithLifecycle()
    val dbData by viewModel.dbData.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                MainUiState.Loading -> {
                    Text("Loading", style = TextStyle(color = Color.Black))
                }
                is MainUiState.Error -> {
                    Text("Error : ${(state as MainUiState.Error).throwable?.message}", style = TextStyle(color = Color.Black))
                }
                is MainUiState.Success -> {
                    Text("Success : ${(state as MainUiState.Success).data}", style = TextStyle(color = Color.Black))
                }
            }

            Text("Database Movies:${dbData.size}", style = TextStyle(color = Color.Black))
            Button(
                onClick = {
                    viewModel.insertMovie()
                },
            ) {
                Text("Click Me", style = TextStyle(color = Color.Black))
            }
        }
    }
}
