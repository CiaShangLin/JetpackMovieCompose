package com.shang.jetpackmoviecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shang.collect.navigation.collectScreen
import com.shang.designsystem.component.JMBackground
import com.shang.designsystem.component.JMNavigationSuiteScaffold
import com.shang.designsystem.theme.Error
import com.shang.designsystem.theme.JetpackMovieComposeTheme
import com.shang.history.navigation.historyScreen
import com.shang.home.navigation.homeScreen
import com.shang.jetpackmoviecompose.navigation.MainNavItem
import com.shang.jetpackmoviecompose.utils.LanguageSettingUtils
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.moviedetail.navigation.movieDetailScreen
import com.shang.moviedetail.navigation.navigateToMovieDetail
import com.shang.search.navigation.searchScreen
import com.shang.setting.navigation.settingsScreen
import com.shang.ui.ErrorScreen
import com.shang.ui.LoadingScreen
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
            val userData by viewModel.userData.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                configuration.value is MainUiState.Loading
            }
            LaunchedEffect(userData.languageMode) {
                LanguageSettingUtils.updateActivityLocale(
                    activity = this@MainActivity,
                    languageMode = userData.languageMode,
                )
            }
            LanguageProvider(languageMode = userData.languageMode) {
                val navController = rememberNavController()
                ThemeProvider(themeMode = userData.themeMode) {
                    MainScreen(configuration.value, navController, onRetry = {
                        viewModel.retryConfiguration()
                    })
                }
            }
        }
    }
}

@Composable
private fun LanguageProvider(
    languageMode: LanguageMode,
    content: @Composable () -> Unit,
) {
    key(languageMode) {
        content()
    }
}

@Composable
private fun ThemeProvider(
    themeMode: ThemeMode,
    content: @Composable () -> Unit,
) {
    val isSystemDarkTheme = isSystemInDarkTheme()

    val isDarkTheme = remember(themeMode, isSystemDarkTheme) {
        when (themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.SYSTEM -> isSystemDarkTheme
        }
    }

    JetpackMovieComposeTheme(darkTheme = isDarkTheme) {
        content()
    }
}

@Composable
fun MainScreen(mainUiState: MainUiState, navController: NavHostController, onRetry: () -> Unit) {
    JMBackground(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues()),
    ) {
        when (mainUiState) {
            is MainUiState.Loading -> {
                MainLoadingScreen()
            }

            is MainUiState.Error -> {
                MainErrorScreen(mainUiState.throwable as Exception?, onRetry = onRetry)
            }

            is MainUiState.Success -> {
                SuccessScreen(navController = navController)
            }
        }
    }
}

@Composable
fun MainLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LoadingScreen()
    }
}

@Composable
fun MainErrorScreen(exception: Exception?, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ErrorScreen(
            onRetry = onRetry,
            throwable = exception,
        )
    }
}

@Composable
fun SuccessScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    JMNavigationSuiteScaffold(
        navigationSuiteItems = {
            MainNavItem.entries.forEach { item ->
                item(
                    selected = currentDestination?.route == item.route,
                    onClick = {
                        if (currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            item.unselectedIcon,
                            contentDescription = stringResource(item.iconTextId),
                        )
                    },
                    selectedIcon = {
                        Icon(
                            item.selectedIcon,
                            contentDescription = stringResource(item.iconTextId),
                        )
                    },
                    label = {
                        Text(stringResource(item.titleTextId))
                    },
                )
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = MainNavItem.HOME.route,
        ) {
            homeScreen(
                onMovieClick = { movieId ->
                    navController.navigateToMovieDetail(movieId)
                },
            )
            collectScreen()
            searchScreen()
            historyScreen()
            settingsScreen()
            movieDetailScreen(onBackClick = {
                navController.popBackStack()
            }, onMovieClick = {
                navController.navigateToMovieDetail(it.movieCardId)
            })
        }
    }
}
