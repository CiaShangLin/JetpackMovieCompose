package com.shang.jetpackmoviecompose.globalData


import com.shang.jetpackmovie.bean.ConfigurationBean


//object Configuration {
//
//    const val CONFIGURATION = "CONFIGURATION"
//    private var mConfigurationBean: ConfigurationBean? = null
//
//    fun setConfiguration(configurationBean: ConfigurationBean) {
//        if (mConfigurationBean == null) {
//            SharedPreUtils.getInstance().setValue(CONFIGURATION, configurationBean.toString())
//        }
//        mConfigurationBean = configurationBean
//    }
//
//    fun getConfiguration(): ConfigurationBean? {
//        if (mConfigurationBean == null) {
//            val tempData = SharedPreUtils.getInstance().getValue(CONFIGURATION, "")
//            if (tempData.isEmpty()) {
//                mConfigurationBean = Gson().fromJson(tempData, ConfigurationBean::class.java)
//            }
//        }
//        return mConfigurationBean
//    }
//}