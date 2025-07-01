package com.shang.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * Now in Android navigation default values.
 */
object JMNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@Composable
fun JMNavigationSuiteScaffold(
    navigationSuiteItems: JMNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = JMNavigationDefaults.navigationContentColor(),
            selectedTextColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = JMNavigationDefaults.navigationContentColor(),
            indicatorColor = JMNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = JMNavigationDefaults.navigationContentColor(),
            selectedTextColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = JMNavigationDefaults.navigationContentColor(),
            indicatorColor = JMNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = JMNavigationDefaults.navigationContentColor(),
            selectedTextColor = JMNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = JMNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            JMNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        modifier = modifier,
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationBarContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationRailContainerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationRailContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationDrawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationDrawerContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        content()
    }
}

class JMNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

/**
 * JMNavigationSuiteScaffold 預覽範例。
 * 展示兩個 navigation item，並以 Box 作為內容。
 */
@Preview(showBackground = true)
@Composable
fun PreviewJMNavigationSuiteScaffold() {
    JMNavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Filled.Home, contentDescription = "首頁") },
                label = { Text("首頁") },
            )
            item(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Filled.Person, contentDescription = "搜尋") },
                label = { Text("搜尋") },
            )
            item(
                selected = false,
                onClick = {},
                icon = { Icon(Icons.Filled.Settings, contentDescription = "設定") },
                label = { Text("設定") },
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("內容區塊", modifier = Modifier)
        }
    }
}
