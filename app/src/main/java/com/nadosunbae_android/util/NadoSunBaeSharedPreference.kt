package com.nadosunbae_android.util

import android.content.Context

object NadoSunBaeSharedPreference {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"

    fun getAccessToken(context: Context): String {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    fun setAccessToken(context: Context, value: String) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putString(ACCESS_TOKEN, value).apply()
    }
}