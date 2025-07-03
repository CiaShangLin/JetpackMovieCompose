package com.shang.collect.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.collect.CollectScreen
import kotlinx.serialization.Serializable

@Serializable
data object CollectRoute

fun NavController.navigateToCollect(navOptions: NavOptions) = navigate(route = CollectRoute, navOptions)

fun NavGraphBuilder.collectScreen() {
    composable<CollectRoute>() {
        CollectScreen()
    }
}
