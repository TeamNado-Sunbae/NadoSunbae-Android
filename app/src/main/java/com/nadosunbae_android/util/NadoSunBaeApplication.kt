package com.nadosunbae_android.util

import android.app.Application
import android.content.Context

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