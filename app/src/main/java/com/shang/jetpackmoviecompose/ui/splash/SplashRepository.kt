package com.shang.jetpackmoviecompose.ui.splash

import com.shang.jetpackmovie.bean.ConfigurationBean
import com.shang.jetpackmoviecompose.api.ConfigurationApi
import com.skydoves.sandwich.ApiResponse

class SplashRepository(private val mConfigurationApi: ConfigurationApi) {

    suspend fun getConfiguration(): ApiResponse<ConfigurationBean> {
        return mConfigurationApi.getConfiguration()
    }
}