package com.prac.market.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyHiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}