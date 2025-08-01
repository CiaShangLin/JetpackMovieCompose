package com.shang.moviedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shang.moviedetail.ui.MovieDetailScreen
import com.shang.ui.MovieCardData

const val ARG_MOVIE_ID = "movieId"
const val MOVIE_DETAIL_ROUTE = "movie_detail_route"

fun NavController.navigateToMovieDetail(movieId: Int, navOptions: NavOptions? = null) {
    navigate("$MOVIE_DETAIL_ROUTE/$movieId", navOptions)
}

fun NavGraphBuilder.movieDetailScreen(
    onBackClick: () -> Unit,
    onMovieClick: (data: MovieCardData) -> Unit,
) {
    composable(
        route = "$MOVIE_DETAIL_ROUTE/{$ARG_MOVIE_ID}",
        arguments = listOf(
            navArgument(ARG_MOVIE_ID) {
                type = NavType.IntType
                defaultValue = 0
            },
        ),
    ) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getInt(ARG_MOVIE_ID) ?: 0
        MovieDetailScreen(movieId = movieId, onBackClick = onBackClick, onMovieClick = onMovieClick)
    }
}
