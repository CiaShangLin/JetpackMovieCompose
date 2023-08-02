package com.shang.jetpackmoviecompose.globalData


import com.shang.jetpackmovie.bean.ConfigurationBean
import com.shang.jetpackmoviecompose.utils.MMKVUtil
import com.shang.jetpackmoviecompose.utils.MoshiUtil
import com.tencent.mmkv.MMKV


object Configuration {

    private const val CONFIGURATION = "CONFIGURATION"
    private var mConfigurationBean: ConfigurationBean? = null

    fun setConfiguration(configurationBean: ConfigurationBean) {
        if (mConfigurationBean == null) {
            val json = MoshiUtil.toJson(configurationBean, ConfigurationBean::class.java)
            MMKVUtil.encode(CONFIGURATION,json)
        }
        mConfigurationBean = configurationBean
    }

    fun getConfiguration(): ConfigurationBean? {
        if (mConfigurationBean == null) {
            val tempData = MMKVUtil.decode(CONFIGURATION,"")
            tempData.let {
                mConfigurationBean = MoshiUtil.fromJson(it, ConfigurationBean::class.java)
            }
        }
        return mConfigurationBean
    }
}