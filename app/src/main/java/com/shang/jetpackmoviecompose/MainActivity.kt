package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.shang.jetpackmoviecompose.theme.JetpackMovieComposeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMovieGenres()
        setContent {
            JetpackMovieComposeTheme {
                Scaffold { _ ->

                    Text("Hello Jetpack Compose!")
                }
            }
        }
    }
}
