package com.shang.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.history.ui.HistoryScreen

const val HISTORY_ROUTE = "history_route"

fun NavController.navigateToHistory(navOptions: NavOptions) = navigate(route = HISTORY_ROUTE, navOptions)

/**
 * 註冊歷史紀錄頁路由至 [NavGraphBuilder]。
 *
 * @param onMovieClick 點擊電影卡片時的回呼，帶入該電影的 movieId
 */
fun NavGraphBuilder.historyScreen(
    onMovieClick: (Int) -> Unit,
) {
    composable(route = HISTORY_ROUTE) {
        HistoryScreen(
            onMovieClick = onMovieClick,
        )
    }
}
