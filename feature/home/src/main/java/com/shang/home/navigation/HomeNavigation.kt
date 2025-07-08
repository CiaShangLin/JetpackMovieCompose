package com.shang.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.home.ui.HomeScreen

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onMovieClick: (Int) -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onMovieClick = onMovieClick,
        )
    }
}
