package com.shang.collect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.collect.ui.CollectScreen

const val COLLECT_ROUTE = "collect_route"

fun NavController.navigateToCollect(navOptions: NavOptions) = navigate(route = COLLECT_ROUTE, navOptions)

/**
 * 註冊收藏頁路由至 [NavGraphBuilder]。
 *
 * @param onMovieClick 點擊電影卡片時的回呼，帶入該電影的 movieId
 */
fun NavGraphBuilder.collectScreen(
    onMovieClick: (Int) -> Unit,
) {
    composable(route = COLLECT_ROUTE) {
        CollectScreen(
            onMovieClick = onMovieClick,
        )
    }
}
