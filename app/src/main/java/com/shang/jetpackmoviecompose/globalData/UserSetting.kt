package com.shang.jetpackmoviecompose.globalData

import androidx.appcompat.app.AppCompatDelegate
import com.shang.jetpackmoviecompose.utils.MMKVUtil

object UserSetting {
    private const val THEME = "THEME"

    var theme: Int
        get() = MMKVUtil.decode(THEME,AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        set(value) {
            AppCompatDelegate.setDefaultNightMode(value)
            MMKVUtil.encode(THEME,value)
        }
}