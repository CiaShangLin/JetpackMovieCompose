package com.shang.jetpackmoviecompose.core

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


class JetpackMovieApplication : MultiDexApplication() {


    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = this
//        AppCompatDelegate.setDefaultNightMode(UserSetting.theme)
//
//        startKoin {
//            androidLogger()
//            androidContext(this@JetpackMovieApplication)
//            modules(
//                roomModule,
//                networkModule,
//                homeViewModelModule,
//                favorViewModelModule,
//                settingViewModel,
//                splashViewModelModule,
//                genresViewModule,
//                detailViewModel
//            )
//        }
    }
}