package com.shang.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute.toString(), navOptions)

// fun NavGraphBuilder.composableHomeScreen(
//    topicDestination: NavGraphBuilder.() -> Unit,
// ) {
//    composable(route = HomeRoute.toString()){
//        HomeScreen()
//    }
// }
