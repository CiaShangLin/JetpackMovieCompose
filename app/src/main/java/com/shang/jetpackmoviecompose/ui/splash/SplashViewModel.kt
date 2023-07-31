package com.shang.jetpackmoviecompose.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.jetpackmovie.bean.ConfigurationBean
import com.shang.jetpackmoviecompose.globalData.Configuration
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val mSplashRepository: SplashRepository) :
    ViewModel() {

    private val mConfigurationFlow = MutableSharedFlow<ApiResponse<ConfigurationBean>>()
    val configurationFlow: SharedFlow<ApiResponse<ConfigurationBean>> = mConfigurationFlow

    init {
        getConfiguration()
    }

   private fun getConfiguration() {
        viewModelScope.launch {
            val configuration = mSplashRepository.getConfiguration()
            configuration.suspendOnSuccess {
                Configuration.setConfiguration(this.data)
            }
            mConfigurationFlow.emit(configuration)
        }
    }
}