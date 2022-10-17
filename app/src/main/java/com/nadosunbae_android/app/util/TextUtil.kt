package com.nadosunbae_android.app.util

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.nadosunbae_android.app.R

fun TextView.setTextSemiBold(isBold: Boolean) {
    this.typeface = ResourcesCompat.getFont(
        this.context,
        if (isBold) R.font.pretendard_semibold else R.font.pretendard_regular
    )
}

fun String.setTextChange(): String {
    if(this.isNotEmpty()){
        val index = 33
        return this.substring(0, index) + "\n" + this.substring(index)
    }
    return this
}