package com.shang.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 封裝專案共用的 ScrollableTabRow 樣式
 *
 * @param selectedIndex 當前選中索引
 * @param onTabSelected Tab 點擊回調
 * @param modifier 外部修飾
 */
@Composable
fun JMScrollableTabRow(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabContent: @Composable () -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        edgePadding = 0.dp,
        modifier = modifier.height(44.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
    ) {
        tabContent()
    }
}

/**
 * 封裝專案共用的 Tab 元素
 *
 * @param text 顯示文本
 */
@Composable
fun JMTab(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
