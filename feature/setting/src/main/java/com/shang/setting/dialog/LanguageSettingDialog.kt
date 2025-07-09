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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shang.model.LanguageMode
import com.shang.setting.R

@Composable
fun LanguageSettingDialog(
    onDismissRequest: () -> Unit,
    currentLanguage: LanguageMode,
    onLanguageSelected: (LanguageMode) -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        LanguageSettingContent(
            onDismissRequest = onDismissRequest,
            currentLanguage = currentLanguage,
            onLanguageSelected = onLanguageSelected,
        )
    }
}

@Composable
private fun LanguageSettingContent(
    onDismissRequest: () -> Unit,
    currentLanguage: LanguageMode = LanguageMode.TRADITIONAL_CHINESE,
    onLanguageSelected: (LanguageMode) -> Unit,
) {
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
                    text = stringResource(R.string.language_selection_title),
                    style = MaterialTheme.typography.titleLarge,
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

            // 語言選項
            Column(
                modifier = Modifier.selectableGroup(),
            ) {
                LanguageOption(
                    text = stringResource(R.string.language_system_default),
                    selected = currentLanguage == LanguageMode.SYSTEM_DEFAULT,
                    onClick = { onLanguageSelected(LanguageMode.SYSTEM_DEFAULT) },
                )
                LanguageOption(
                    text = stringResource(R.string.language_traditional_chinese),
                    selected = currentLanguage == LanguageMode.TRADITIONAL_CHINESE,
                    onClick = { onLanguageSelected(LanguageMode.TRADITIONAL_CHINESE) },
                )
                LanguageOption(
                    text = stringResource(R.string.language_simplified_chinese),
                    selected = currentLanguage == LanguageMode.SIMPLIFIED_CHINESE,
                    onClick = { onLanguageSelected(LanguageMode.SIMPLIFIED_CHINESE) },
                )
                LanguageOption(
                    text = stringResource(R.string.language_english),
                    selected = currentLanguage == LanguageMode.ENGLISH,
                    onClick = { onLanguageSelected(LanguageMode.ENGLISH) },
                )
            }
        }
    }
}

@Composable
private fun LanguageOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

/**
 * 預覽 LanguageSettingDialog 的不同狀態
 *
 * 注意：由於 Dialog 在預覽中無法正常顯示，
 * 這裡提取對話框內容進行預覽
 */
@Preview(name = "繁體中文選中", showBackground = true)
@Composable
private fun LanguageSettingDialogPreview_Chinese() {
    MaterialTheme {
        var selectedLanguage by remember { mutableStateOf(LanguageMode.TRADITIONAL_CHINESE) }
        LanguageSettingContent(
            currentLanguage = selectedLanguage,
            onLanguageSelected = { selectedLanguage = it },
            onDismissRequest = { },
        )
    }
}

@Preview(name = "英文選中", showBackground = true)
@Composable
private fun LanguageSettingDialogPreview_English() {
    MaterialTheme {
        var selectedLanguage by remember { mutableStateOf(LanguageMode.ENGLISH) }
        LanguageSettingContent(
            currentLanguage = selectedLanguage,
            onLanguageSelected = { selectedLanguage = it },
            onDismissRequest = { },
        )
    }
}
