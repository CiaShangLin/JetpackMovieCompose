package com.shang.home

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shang.designsystem.component.JMNavigationSuiteScaffold
import com.shang.home.navigation.HomeNavItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HomeNavItem.HOME.route

    JMNavigationSuiteScaffold(
        navigationSuiteItems = {
            HomeNavItem.entries.forEach { item ->
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
            startDestination = HomeNavItem.HOME.route,
        ) {
            composable(route = HomeNavItem.HOME.route) {
                // HomeContent(viewModel)
            }
            composable(route = HomeNavItem.COLLECTION.route) {
                // SettingContent()
            }
            composable(route = HomeNavItem.SEARCH.route) {
                // SearchContent()
            }
            composable(route = HomeNavItem.HISTORY.route) {
                // SettingContent()
            }
            composable(route = HomeNavItem.SETTING.route) {
                // SettingContent()
            }
        }
    }
}
