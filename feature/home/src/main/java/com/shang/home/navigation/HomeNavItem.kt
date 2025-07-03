package com.shang.home.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.shang.home.R

enum class HomeNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: String,
) {

    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.nav_home,
        titleTextId = R.string.nav_home,
        route = "home",
    ),

    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Outlined.Search,
        iconTextId = R.string.nav_search,
        titleTextId = R.string.nav_search,
        route = "search",
    ),

    COLLECTION(
        selectedIcon = Icons.Rounded.Search, // Placeholder, replace with actual collection icons
        unselectedIcon = Icons.Outlined.Search, // Placeholder, replace with actual collection icons
        iconTextId = R.string.nav_favor,
        titleTextId = R.string.nav_favor,
        route = "collection",
    ),

    HISTORY(
        selectedIcon = Icons.Rounded.Search, // Placeholder, replace with actual history icons
        unselectedIcon = Icons.Outlined.Search, // Placeholder, replace with actual history icons
        iconTextId = R.string.nav_history,
        titleTextId = R.string.nav_history,
        route = "history",
    ),

    SETTING(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconTextId = R.string.nav_setting,
        titleTextId = R.string.nav_setting,
        route = "setting",
    ),
}
