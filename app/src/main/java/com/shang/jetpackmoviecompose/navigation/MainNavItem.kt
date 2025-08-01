package com.shang.jetpackmoviecompose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.shang.collect.navigation.COLLECT_ROUTE
import com.shang.history.navigation.HISTORY_ROUTE
import com.shang.home.navigation.HOME_ROUTE
import com.shang.jetpackmoviecompose.R
import com.shang.search.navigation.SEARCH_ROUTE
import com.shang.setting.navigation.SETTINGS_ROUTE

enum class MainNavItem(
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
        route = HOME_ROUTE,
    ),

    COLLECT(
        selectedIcon = Icons.Rounded.Favorite,
        unselectedIcon = Icons.Outlined.Favorite,
        iconTextId = R.string.nav_favor,
        titleTextId = R.string.nav_favor,
        route = COLLECT_ROUTE,
    ),

    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Outlined.Search,
        iconTextId = R.string.nav_search,
        titleTextId = R.string.nav_search,
        route = SEARCH_ROUTE,
    ),

    HISTORY(
        selectedIcon = Icons.Rounded.History,
        unselectedIcon = Icons.Outlined.History,
        iconTextId = R.string.nav_history,
        titleTextId = R.string.nav_history,
        route = HISTORY_ROUTE,
    ),

    SETTING(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconTextId = R.string.nav_setting,
        titleTextId = R.string.nav_setting,
        route = SETTINGS_ROUTE,
    ),
}
