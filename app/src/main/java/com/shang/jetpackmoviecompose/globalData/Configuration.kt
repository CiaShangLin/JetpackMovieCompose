package com.shang.jetpackmoviecompose.globalData


import android.util.Log
import com.google.gson.Gson
import com.shang.jetpackmovie.bean.ConfigurationBean
import com.shang.jetpackmoviecompose.utils.MoshiUtil
import com.squareup.moshi.Moshi
import com.tencent.mmkv.MMKV


object Configuration {

    const val CONFIGURATION = "CONFIGURATION"
    private var mConfigurationBean: ConfigurationBean? = null

    fun setConfiguration(configurationBean: ConfigurationBean) {
        if (mConfigurationBean == null) {
            val json = MoshiUtil.toJson(configurationBean, ConfigurationBean::class.java)
            MMKV.defaultMMKV().encode(CONFIGURATION, json)
        }
        mConfigurationBean = configurationBean
    }

    fun getConfiguration(): ConfigurationBean? {
        if (mConfigurationBean == null) {
            val tempData = MMKV.defaultMMKV().decodeString(CONFIGURATION, "")
            tempData?.let {
                mConfigurationBean = MoshiUtil.fromJson(it, ConfigurationBean::class.java)
            }
        }
        return mConfigurationBean
    }
}