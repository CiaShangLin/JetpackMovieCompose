package com.shang.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shang.setting.SettingScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSetting(navOptions: NavOptions) = navigate(route = SettingsRoute, navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsRoute>() {
        SettingScreen()
    }
}
