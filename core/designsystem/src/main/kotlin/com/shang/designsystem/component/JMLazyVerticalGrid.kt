package com.shang.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass

/**
 * 根據視窗寬度自動調整欄數的 LazyVerticalGrid
 * @param windowAdaptiveInfo 當前視窗適應資訊，預設自動取得
 */
@Composable
fun JMLazyVerticalGrid(
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    contentPadding: PaddingValues = PaddingValues(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    content: LazyGridScope.() -> Unit,
) {
    // 根據螢幕寬度自動調整 Grid 欄數，提升 UI 響應性與可讀性
    val columns = when (windowAdaptiveInfo.windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 2 // 手機尺寸 (< 600dp)
        WindowWidthSizeClass.MEDIUM -> 3 // 平板直向或小平板 (600dp - 839dp)
        WindowWidthSizeClass.EXPANDED -> 4 // 平板橫向或桌面 (>= 840dp)
        else -> 2 // 預設值
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        contentPadding = contentPadding, // 外部間距
        verticalArrangement = verticalArrangement, // 垂直間距
        horizontalArrangement = horizontalArrangement, // 水平間距
        content = content,
    )
}
