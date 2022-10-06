package com.nadosunbae_android.app.util

import com.nadosunbae_android.app.R

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

fun imageSelect(imageId: Int): Int {
    when (imageId) {
        1 -> return R.drawable.mask_group_5
        2 -> return R.drawable.mask_group_4
        3 -> return R.drawable.mask_group_2
        4 -> return R.drawable.mask_group_1
        5 -> return R.drawable.mask_group_3
    }
    return R.drawable.mask_group_4
}