package com.shang.collect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.collect.ui.CollectScreen

const val COLLECT_ROUTE = "collect_route"

fun NavController.navigateToCollect(navOptions: NavOptions) = navigate(route = COLLECT_ROUTE, navOptions)

fun NavGraphBuilder.collectScreen() {
    composable(route = COLLECT_ROUTE) {
        CollectScreen()
    }
}
