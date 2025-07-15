package com.shang.jetpackmoviecompose.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.shang.model.LanguageMode
import java.util.Locale

/**
 * 語言設定工具類
 * 負責處理應用程式的語言切換功能
 *
 * 功能包括：
 * - 透過 LanguageMode 設定應用語言
 * - 監聽使用者資料中的語言變化
 * - 更新 Context 和 Activity 的語言設定
 */
object LanguageSettingUtils {

    /**
     * 根據 LanguageMode 取得對應的 Locale
     *
     * @param languageMode 語言模式
     * @return 對應的 Locale 物件
     */
    private fun getLocaleFromLanguageMode(languageMode: LanguageMode): Locale {
        return when (languageMode) {
            LanguageMode.TRADITIONAL_CHINESE -> Locale.Builder()
                .setLanguage("zh")
                .setRegion("TW")
                .build()
            LanguageMode.ENGLISH -> Locale.Builder()
                .setLanguage("en")
                .setRegion("US")
                .build()
            LanguageMode.SYSTEM_DEFAULT -> Locale.getDefault()
        }
    }

    /**
     * 更新 Context 的 Locale 設定
     *
     * @param context 要更新的 Context
     * @param locale 目標 Locale
     * @return 更新後的 Context
     */
    fun updateContextLocale(context: Context, languageMode: LanguageMode): Context {
        val configuration = Configuration(context.resources.configuration)
        val locale = getLocaleFromLanguageMode(languageMode)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(LocaleList(locale))
            context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
            context.createConfigurationContext(configuration)
        }
    }

    /**
     * 更新 Activity 的 Locale 設定
     *
     * @param activity 要更新的 Activity
     * @param locale 目標 Locale
     */
    fun updateActivityLocale(activity: Activity, languageMode: LanguageMode) {
        val locale = getLocaleFromLanguageMode(languageMode)
        val resources = activity.resources
        val configuration = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(LocaleList(locale))
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
        }

        @Suppress("DEPRECATION")
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
