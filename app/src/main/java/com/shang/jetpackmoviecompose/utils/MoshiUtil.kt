package com.shang.jetpackmoviecompose.utils

import com.squareup.moshi.Moshi

object MoshiUtil {
    val moshi = Moshi.Builder().build()

    inline fun <reified T> fromJson(json: String, c: Class<T>): T? {
        val adapter = moshi.adapter(c)
        return adapter.fromJson(json)
    }

    inline fun <reified T> toJson(bean: T, c: Class<T>): String {
        val adapter = moshi.adapter(c)
        return adapter.toJson(bean)
    }
}