package com.shang.jetpackmoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shang.designsystem.component.JMBackground
import com.shang.designsystem.theme.JetpackMovieComposeTheme
import com.shang.home.HomeScreen
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
            JetpackMovieComposeTheme {
                MainScreen()
            }
        }
    }
}

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navItems = listOf(
        NavItem("home", Icons.Default.Home, "首頁"),
        NavItem("search", Icons.Default.Search, "搜尋"),
        NavItem("setting", Icons.Default.Settings, "設定"),
    )
    val currentRoute = navController.currentBackStackEntryFlow
        .collectAsState(initial = navController.currentDestination?.route ?: "home").value

    JMBackground() {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                navItems.forEach { item ->
                    item(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                    )
                }
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = "home",
            ) {
                composable(route = "home") {
                    HomeScreen()
                }
                composable(route = "setting") {
                    SettingScreen()
                }
                composable(route = "search") {
                    SearchScreen()
                }
            }
        }
//        NavigationBar {
//            NavigationBarItem()
//            NavigationRailItem()
//        }
//
//        NavigationRail() {
//
//        }
    }
}
