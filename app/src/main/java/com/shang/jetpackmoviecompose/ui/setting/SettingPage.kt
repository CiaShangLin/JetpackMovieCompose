package com.shang.jetpackmoviecompose.ui.setting

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.shang.jetpackmoviecompose.R
import com.shang.jetpackmoviecompose.ui.dialog.ChangeThemeDialog
import com.shang.jetpackmoviecompose.ui.dialog.DeveloperDialog
import com.shang.jetpackmoviecompose.ui.dialog.DisclaimerDialog
import com.shang.jetpackmoviecompose.ui.theme.SettingDividingLine

enum class Setting(@StringRes val title: Int) {
    THEME(R.string.theme),
    DEVELOPER(R.string.developers),
    DISCLAIMER(R.string.disclaimer),
}

@Composable
fun SettingPage() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column() {
            enumValues<Setting>().forEach {
                SettingItem(it)
                DividingLine()
            }
        }
    }
}

@Composable
fun SettingItem(setting: Setting) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog.value = true
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = setting.title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.icon_setting_arrow),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }

    if (showDialog.value) {
        when (setting) {
            Setting.THEME -> {
                ChangeThemeDialog {
                    showDialog.value = false
                }
            }
            Setting.DEVELOPER -> {
                DeveloperDialog {
                    showDialog.value = false
                }
            }
            Setting.DISCLAIMER -> {
                DisclaimerDialog {
                    showDialog.value = false
                }
            }
        }
    }
}

@Composable
fun DividingLine() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(SettingDividingLine)
    )
}

