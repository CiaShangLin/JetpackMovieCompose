package com.shang.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.search.ui.SearchScreen

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions) = navigate(route = SEARCH_ROUTE, navOptions)

/**
 * 註冊搜尋頁路由至 [NavGraphBuilder]。
 *
 * @param onMovieClick 點擊電影卡片時的回呼，帶入該電影的 movieId
 */
fun NavGraphBuilder.searchScreen(
    onMovieClick: (Int) -> Unit,
) {
    composable(route = SEARCH_ROUTE) {
        SearchScreen(
            onMovieClick = onMovieClick,
        )
    }
}
