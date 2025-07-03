package com.shang.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.setting.SettingScreen

const val SETTINGS_ROUTE = "setting_route"

fun NavController.navigateToSetting(navOptions: NavOptions) = navigate(route = SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable(route = SETTINGS_ROUTE) {
        SettingScreen()
    }
}
