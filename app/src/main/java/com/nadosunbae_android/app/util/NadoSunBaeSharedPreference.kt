package com.nadosunbae_android.app.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object NadoSunBaeSharedPreference {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    private const val USER_ID = "USER_ID"
    private const val TIME_FLAG = "TIME_FLAG"
    const val DAU_FLAG = "DAU_FLAG"
    const val WAU_FLAG = "WAU_FLAG"
    const val MAU_FLAG = "MAU_FLAG"
    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

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

    fun getUserId(context: Context): Int {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getInt(USER_ID, -1)
    }

    fun setUserId(context: Context, value: Int) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putInt(USER_ID, value).apply()
    }

    fun setTimeFlag(context: Context, value: Date) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        preferences.edit().putString(TIME_FLAG, dateFormat.format(value)).apply()
    }

    fun getTimeFlag(context: Context): Date? {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val dateString = preferences.getString(TIME_FLAG, null)

        return if (dateString != null) {
            val dateFormat = SimpleDateFormat(DATE_FORMAT)
            dateFormat.parse(dateString)
        } else null
    }

    fun setFlag(context: Context, key: String, flag: Boolean) {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(key, flag).apply()
    }

    fun getFlag(context: Context, key: String): Boolean {
        val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, false)
    }

    //userId 저장
}