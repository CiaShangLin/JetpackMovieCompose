package com.shang.setting.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsApplications
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shang.model.ThemeMode
import com.shang.setting.R

@Composable
fun ThemeSettingDialog(
    onDismissRequest: () -> Unit,
    currentTheme: ThemeMode = ThemeMode.SYSTEM,
    onThemeSelected: (ThemeMode) -> Unit = {},
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .widthIn(
                    min = 280.dp, // Material Design 最小寬度
                    max = 560.dp, // Material Design 最大寬度
                )
                .fillMaxWidth(0.9f) // 在限制範圍內盡量填滿
                .wrapContentHeight(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp),
            ) {
                // 標題列
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.theme_selection_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                // 主題選項
                Column(
                    modifier = Modifier.selectableGroup(),
                ) {
                    ThemeOption(
                        text = stringResource(R.string.theme_system_default),
                        selected = currentTheme == ThemeMode.SYSTEM,
                        onClick = { onThemeSelected(ThemeMode.SYSTEM) },
                        icon = Icons.Filled.SettingsApplications,
                    )
                    ThemeOption(
                        text = stringResource(R.string.theme_light_mode),
                        selected = currentTheme == ThemeMode.LIGHT,
                        onClick = { onThemeSelected(ThemeMode.LIGHT) },
                        icon = Icons.Filled.LightMode,
                    )

                    ThemeOption(
                        text = stringResource(R.string.theme_dark_mode),
                        selected = currentTheme == ThemeMode.DARK,
                        onClick = { onThemeSelected(ThemeMode.DARK) },
                        icon = Icons.Filled.DarkMode,
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null, // 由父層 Row 處理點擊
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )

        // 添加主題圖示
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp, end = 8.dp)
                    .size(20.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                },
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.padding(start = if (icon == null) 16.dp else 0.dp),
        )
    }
}

/**
 * 預覽 ThemeSettingDialog 的不同狀態
 *
 * 注意：由於 Dialog 在預覽中無法正常顯示，
 * 這裡提取對話框內容進行預覽
 */
@Composable
private fun ThemeSettingDialogContent(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Card(
        modifier = Modifier
            .widthIn(min = 280.dp, max = 560.dp)
            .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            // 標題列
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "選擇主題", // 硬編碼用於預覽
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.size(48.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "關閉",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            // 主題選項
            Column(
                modifier = Modifier.selectableGroup(),
            ) {
                ThemeOption(
                    text = "亮色模式",
                    selected = currentTheme == ThemeMode.LIGHT,
                    onClick = { onThemeSelected(ThemeMode.LIGHT) },
                    icon = Icons.Filled.LightMode,
                )

                ThemeOption(
                    text = "黑暗模式",
                    selected = currentTheme == ThemeMode.DARK,
                    onClick = { onThemeSelected(ThemeMode.DARK) },
                    icon = Icons.Filled.DarkMode,
                )

                ThemeOption(
                    text = "系統預設",
                    selected = currentTheme == ThemeMode.SYSTEM,
                    onClick = { onThemeSelected(ThemeMode.SYSTEM) },
                    icon = Icons.Filled.SettingsApplications,
                )
            }
        }
    }
}

@Preview(name = "亮色主題選中", showBackground = true)
@Composable
private fun ThemeSettingDialogPreview_Light() {
    MaterialTheme {
        var selectedTheme by remember { mutableStateOf(ThemeMode.LIGHT) }
        ThemeSettingDialogContent(
            currentTheme = selectedTheme,
            onThemeSelected = { selectedTheme = it },
            onDismissRequest = { },
        )
    }
}

@Preview(name = "黑暗主題選中", showBackground = true)
@Composable
private fun ThemeSettingDialogPreview_Dark() {
    MaterialTheme {
        var selectedTheme by remember { mutableStateOf(ThemeMode.DARK) }
        ThemeSettingDialogContent(
            currentTheme = selectedTheme,
            onThemeSelected = { selectedTheme = it },
            onDismissRequest = { },
        )
    }
}

@Preview(name = "系統預設選中", showBackground = true)
@Composable
private fun ThemeSettingDialogPreview_System() {
    MaterialTheme {
        var selectedTheme by remember { mutableStateOf(ThemeMode.SYSTEM) }
        ThemeSettingDialogContent(
            currentTheme = selectedTheme,
            onThemeSelected = { selectedTheme = it },
            onDismissRequest = { },
        )
    }
}
