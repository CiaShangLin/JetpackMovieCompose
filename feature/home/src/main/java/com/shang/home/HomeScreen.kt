package com.shang.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
}

// @Composable
// fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
//    val currentRoute = navController.currentBackStackEntryFlow
//        .collectAsState(
//            initial = navController.currentDestination?.route ?: MainNavItem.HOME.route,
//        ).value
//
//    JMBackground() {
//        JMNavigationSuiteScaffold(
//            navigationSuiteItems = {
//                MainNavItem.entries.forEach { item ->
//                    item(
//                        selected = currentRoute == item.route,
//                        onClick = {
//                            if (currentRoute != item.route) {
//                                navController.navigate(item.route) {
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        },
//                        icon = {
//                            Icon(
//                                item.unselectedIcon,
//                                contentDescription = stringResource(item.iconTextId),
//                            )
//                        },
//                        selectedIcon = {
//                            Icon(
//                                item.selectedIcon,
//                                contentDescription = stringResource(item.iconTextId),
//                            )
//                        },
//                        label = {
//                            Text(stringResource(item.titleTextId))
//                        },
//                    )
//                }
//            },
//        ) {
//            NavHost(
//                navController = navController,
//                startDestination = MainNavItem.HOME.route,
//            ) {
//                composable(route = MainNavItem.HOME.route) {
//                    HomeScreen()
//                }
//                composable(route = MainNavItem.SEARCH.route) {
//                    SearchScreen()
//                }
//                composable(route = MainNavItem.SETTING.route) {
//                    SettingScreen()
//                }
//            }
//        }
//    }
// }
