package com.nadosunbae_android.app.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

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