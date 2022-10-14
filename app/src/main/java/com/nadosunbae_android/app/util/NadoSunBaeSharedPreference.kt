package com.nadosunbae_android.app.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

enum class ActiveUser {
    DAU, WAU, MAU
}

object NadoSunBaeSharedPreference {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    private const val USER_ID = "USER_ID"

    fun getAccessToken(context: Context): String {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getString(ACCESS_TOKEN, "") ?: ""
    }

    fun setAccessToken(context: Context, value: String) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putString(ACCESS_TOKEN, value).apply()
    }

    fun removeAccessToken(context: Context) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().remove(ACCESS_TOKEN).apply()
    }

    fun getRefreshToken(context: Context): String {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    fun setRefreshToken(context: Context, value: String) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.edit().putString(REFRESH_TOKEN, value).apply()
    }

    fun removeRefreshToken(context: Context) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().remove(REFRESH_TOKEN).apply()
    }


    // user active
    fun setUserActive(context: Context, calendar: Calendar, type: ActiveUser) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)

        when (type) {
            ActiveUser.DAU -> preferences.edit().putBoolean("${type}_${year}_${month}_${day}", true).apply()
            ActiveUser.WAU -> preferences.edit().putBoolean("${type}_${year}_${calendar.weekYear}", true).apply()
            ActiveUser.MAU -> preferences.edit().putBoolean("${type}_${year}_${month}", true).apply()
        }
    }

    fun getUserActive(context: Context, calendar: Calendar, type: ActiveUser): Boolean {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)

        return when (type) {
            ActiveUser.DAU ->  preferences.getBoolean("${ActiveUser.DAU}_${year}_${month}_${day}", false)
            ActiveUser.WAU -> preferences.getBoolean("${ActiveUser.WAU}_${year}_${calendar.weekYear}", false)
            ActiveUser.MAU -> preferences.getBoolean("${ActiveUser.MAU}_${year}_${month}", false)
        }
    }
}