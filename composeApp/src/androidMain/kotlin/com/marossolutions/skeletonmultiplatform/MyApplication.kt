package com.marossolutions.skeletonmultiplatform

import android.app.Application
import com.marossolutions.skeletonmultiplatform.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }
}
