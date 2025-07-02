package com.shang.model

/**
 * 使用者資料
 * @param configurationBean 電影資料API配置
 */
data class UserData(
    val showCompleted: Boolean,
    val version: Int,
    val configuration: ConfigurationBean,
)
