package com.shang.jetpackmoviecompose.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetpackMovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}