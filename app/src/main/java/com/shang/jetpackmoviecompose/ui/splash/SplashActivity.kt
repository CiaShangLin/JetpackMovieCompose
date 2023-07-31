package com.shang.jetpackmoviecompose.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shang.jetpackmoviecompose.MainActivity
import com.shang.jetpackmoviecompose.globalData.Configuration
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

@AndroidEntryPoint
@SuppressLint("RestrictedApi")
class SplashActivity : ComponentActivity() {

    private val mViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            mViewModel.configurationFlow.collect {
                when (it) {
                    is ApiResponse.Failure.Error -> {

                    }
                    is ApiResponse.Failure.Exception -> {

                    }
                    is ApiResponse.Success -> {
                        MainActivity.start(this@SplashActivity)
                        finish()
                    }
                }
            }
        }
    }
}