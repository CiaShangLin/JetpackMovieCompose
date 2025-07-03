package com.shang.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.history.HistoryScreen

const val HISTORY_ROUTE = "history_route"

fun NavController.navigateToHistory(navOptions: NavOptions) = navigate(route = HISTORY_ROUTE, navOptions)

fun NavGraphBuilder.historyScreen() {
    composable(route = HISTORY_ROUTE) {
        HistoryScreen()
    }
}
