package com.nadosunbae_android.app.di

import android.app.Application
import android.content.Context
import com.nadosunbae_android.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NadoSunBaeApplication : Application() {
    init{
        instance = this
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object{
        var instance: NadoSunBaeApplication? = null
        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}