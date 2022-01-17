package com.nadosunbae_android.util

import android.content.res.Resources
import android.util.TypedValue

class PixelRatio {
    private val displayMetrics
        get() = Resources.getSystem().displayMetrics

    fun dpToPx(dp: Int) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics).toInt()

    fun dpToPxF(dp: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)

}

val Number.dpToPx: Int
    get() = PixelRatio().dpToPx(this.toInt())

val Number.dpToPxF: Float
    get() = PixelRatio().dpToPxF(this.toInt())
