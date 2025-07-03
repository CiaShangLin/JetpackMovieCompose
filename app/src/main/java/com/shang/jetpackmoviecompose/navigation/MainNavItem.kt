package com.shang.jetpackmoviecompose.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Collections
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.shang.collect.navigation.CollectRoute
import com.shang.history.navigation.HistoryRoute
import com.shang.home.navigation.HomeRoute
import com.shang.jetpackmoviecompose.R
import com.shang.search.navigation.SearchRoute
import com.shang.setting.navigation.SettingsRoute
import kotlin.reflect.KClass

enum class MainNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {

    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.nav_home,
        titleTextId = R.string.nav_home,
        route = HomeRoute::class,
    ),

    COLLECTION(
        selectedIcon = Icons.Rounded.Collections,
        unselectedIcon = Icons.Outlined.Collections,
        iconTextId = R.string.nav_favor,
        titleTextId = R.string.nav_favor,
        route = CollectRoute::class,
    ),

    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Outlined.Search,
        iconTextId = R.string.nav_search,
        titleTextId = R.string.nav_search,
        route = SearchRoute::class,
    ),

    HISTORY(
        selectedIcon = Icons.Rounded.History,
        unselectedIcon = Icons.Outlined.History,
        iconTextId = R.string.nav_history,
        titleTextId = R.string.nav_history,
        route = HistoryRoute::class,
    ),

    SETTING(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconTextId = R.string.nav_setting,
        titleTextId = R.string.nav_setting,
        route = SettingsRoute::class,
    ),
}
