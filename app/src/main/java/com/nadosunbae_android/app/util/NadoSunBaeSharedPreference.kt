package com.nadosunbae_android.app.util

import android.content.Context

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

    fun getRefreshToken(context: Context): String {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getString(REFRESH_TOKEN, "") ?: ""
    }

    fun setRefreshToken(context: Context, value: String) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.edit().putString(REFRESH_TOKEN, value).apply()
    }

    fun getUserId(context: Context): Int {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getInt(USER_ID, -1)
    }

    fun setUserId(context: Context, value: Int) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putInt(USER_ID, value).apply()
    }

    //userId 저장
}