package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shang.designsystem.component.JMBackground
import com.shang.designsystem.component.JMNavigationSuiteScaffold
import com.shang.designsystem.theme.JetpackMovieComposeTheme
import com.shang.home.HomeScreen
import com.shang.jetpackmoviecompose.navigation.MainNavItem
import com.shang.search.SearchScreen
import com.shang.setting.SettingScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            JetpackMovieComposeTheme {
                MainScreen(navController, viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val currentRoute = navController.currentBackStackEntryFlow
        .collectAsState(
            initial = navController.currentDestination?.route ?: MainNavItem.HOME.route,
        ).value
//
//    val movieGenreBean = viewModel.data.collectAsStateWithLifecycle()

//    LaunchedEffect(Unit) {
//        Log.d("DEBUG", "$movieGenreBean")
//    }
    JMBackground() {
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
                    HomeScreen()
                }
                composable(route = MainNavItem.SEARCH.route) {
                    SearchScreen()
                }
                composable(route = MainNavItem.SETTING.route) {
                    SettingScreen()
                }
            }
        }
    }
}
