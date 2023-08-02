package com.shang.jetpackmoviecompose.utils

import com.tencent.mmkv.MMKV

object MMKVUtil {
    private val mMMKV = MMKV.defaultMMKV()

    fun <T : Any> encode(key: String, value: T): Boolean {
        return when (value) {
            is String -> {
                mMMKV.encode(key, value)
            }
            is Int -> {
                mMMKV.encode(key, value)
            }
            else -> false
        }
    }

    fun <T : Any> decode(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> mMMKV.decodeString(key, defaultValue) as T
            is Int -> mMMKV.decodeInt(key, defaultValue) as T
            else -> {
                defaultValue
            }
        }
    }

}