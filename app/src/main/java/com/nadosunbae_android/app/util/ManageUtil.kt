package com.nadosunbae_android.app.util

import android.content.Context
import android.content.Intent
import android.util.Log

object ManageUtil {
    fun restartApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent?.component)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)

        Log.d("ManageUtil", "Application Restart")
    }
}