package com.nadosunbae_android.app.di

import android.app.Application
import android.content.Context
import com.nadosunbae_android.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class NadoSunBaeApplication : Application() {
    init{
        instance = this
    }

    companion object{
        var instance: NadoSunBaeApplication? = null
        fun context() : Context {
            return instance!!.applicationContext
        }
    }
}