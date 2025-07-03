package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.shang.designsystem.component.JMBackground
import com.shang.designsystem.theme.JetpackMovieComposeTheme
import com.shang.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val configuration = viewModel.configuration.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                configuration.value is MainUiState.Loading
            }
            JetpackMovieComposeTheme {
                MainScreen(configuration.value)
            }
        }
    }
}

@Composable
fun MainScreen(mainUiState: MainUiState) {
    JMBackground {
        when (mainUiState) {
            is MainUiState.Loading -> {
                // 空的因為splash本身就是Loading
            }
            is MainUiState.Error -> {
                ErrorScreen()
            }
            is MainUiState.Success -> HomeScreen()
        }
    }
}

@Composable
fun ErrorScreen() {
    // 這裡可以做一個Error錯誤刷新POP窗，可以考慮要不要掉slapsh，或是POP窗，或是UI
    Text("ERROR")
}
