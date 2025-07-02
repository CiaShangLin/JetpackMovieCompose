package com.shang.model

/**
 * 使用者資料
 */
data class UserData(
    val showCompleted: Boolean = false,
    val version: Int = 1,
    val configurationBean: ConfigurationBean = ConfigurationBean(),
)
