package com.nadosunbae_android.app.util

import com.nadosunbae_android.R

object ResourceUtil {
}

fun Any.getBackgroundImage(imageId: Int): Int {
    when (imageId) {
        6 -> return R.drawable.img_bg_1
        7 -> return R.drawable.img_bg_2
        8 -> return R.drawable.img_bg_3
        9 -> return R.drawable.img_bg_4
        10 -> return R.drawable.img_bg_5
        11 -> return R.drawable.img_bg_6
        12 -> return R.drawable.img_bg_7
    }
    // default
    return R.drawable.img_bg_1
}