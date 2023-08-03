package com.shang.jetpackmoviecompose.ui.dialog

import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shang.jetpackmoviecompose.R
import com.shang.jetpackmoviecompose.globalData.UserSetting
import com.shang.jetpackmoviecompose.ui.theme.JetpackMovieComposeTheme
import com.shang.jetpackmoviecompose.ui.theme.ThemeRadioButton


enum class Theme(@StringRes val title: Int, val mode: Int) {
    DEFAULT(R.string.system_default_mode, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    LIGHT(R.string.system_light_mode, AppCompatDelegate.MODE_NIGHT_NO),
    DARK(R.string.system_dark_mode, AppCompatDelegate.MODE_NIGHT_YES)
}

@Composable
fun ChangeThemeDialog(onDismissRequest: () -> Unit) {

    val selectTheme = remember {
        mutableStateOf(UserSetting.theme)
    }

    @Composable
    fun item(theme: Theme) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(300.dp)
        ) {
            RadioButton(
                colors = RadioButtonDefaults.colors(
                    ThemeRadioButton
                ),
                selected = (selectTheme.value == theme.mode),
                onClick = {
                    UserSetting.theme = theme.mode
                    selectTheme.value = theme.mode
                })
            Text(
                text = stringResource(id = theme.title),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }

    JetpackMovieComposeTheme(
        darkTheme = when (selectTheme.value) {
            Theme.LIGHT.mode -> false
            Theme.DARK.mode -> true
            else -> isSystemInDarkTheme()
        }
    )
    {
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                modifier = Modifier.padding(0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                enumValues<Theme>().forEachIndexed { index, theme ->
                    item(theme)
                }
            }
        }

    }
}