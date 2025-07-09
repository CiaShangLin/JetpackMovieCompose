package com.shang.model

/**
 * 使用者資料
 * @param configuration 電影資料API配置
 */
data class UserData(
    val configuration: ConfigurationBean,
    val themeMode: ThemeMode,
    val languageMode: LanguageMode,
) {
    companion object {
        fun getDefault(): UserData {
            return UserData(
                configuration = ConfigurationBean(),
                themeMode = ThemeMode.SYSTEM,
                languageMode = LanguageMode.SYSTEM_DEFAULT,
            )
        }
    }
}
