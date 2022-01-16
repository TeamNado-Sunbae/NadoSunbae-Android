package com.nadosunbae_android.util

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.nadosunbae_android.R

object TextUtil {
}

fun TextView.setTextSemiBold(isBold: Boolean) {
    this.typeface = ResourcesCompat.getFont(this.context, if(isBold) R.font.pretendard_semibold else R.font.pretendard_regular)
}