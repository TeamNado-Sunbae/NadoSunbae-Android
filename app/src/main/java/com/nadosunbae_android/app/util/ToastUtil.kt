package com.nadosunbae_android.app.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}