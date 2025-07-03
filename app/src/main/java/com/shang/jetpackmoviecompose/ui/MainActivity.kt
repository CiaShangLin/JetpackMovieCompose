package com.shang.jetpackmoviecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shang.designsystem.component.JMBackground
import com.shang.designsystem.component.JMNavigationSuiteScaffold
import com.shang.designsystem.theme.JetpackMovieComposeTheme
import com.shang.jetpackmoviecompose.ui.MainUiState
import com.shang.jetpackmoviecompose.ui.MainViewModel
import com.shang.jetpackmoviecompose.navigation.MainNavItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val configuration = viewModel.configuration.collectAsState()
            splashScreen.setKeepOnScreenCondition {
                configuration.value is MainUiState.Loading
            }
            JetpackMovieComposeTheme {
                MainScreen(configuration.value, navController)
            }
        }
    }
}

@Composable
fun MainScreen(mainUiState: MainUiState, navController: NavHostController) {
    JMBackground(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues()),
    ) {
        when (mainUiState) {
            is MainUiState.Loading -> {
                // 空的因為splash本身就是Loading
            }
            is MainUiState.Error -> {
                ErrorScreen()
            }
            is MainUiState.Success -> {
                SuccessScreen(navController = navController)
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("ERROR")
    }
}

@Composable
fun SuccessScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainNavItem.HOME.route

    JMNavigationSuiteScaffold(
        navigationSuiteItems = {
            MainNavItem.entries.forEach { item ->
                item(
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
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
            composable(route = MainNavItem.HOME.route) {
                // HomeContent(viewModel)
            }
            composable(route = MainNavItem.COLLECTION.route) {
                // SettingContent()
            }
            composable(route = MainNavItem.SEARCH.route) {
                // SearchContent()
            }
            composable(route = MainNavItem.HISTORY.route) {
                // SettingContent()
            }
            composable(route = MainNavItem.SETTING.route) {
                // SettingContent()
            }
        }
    }
}
