package com.nadosunbae_android.app.util

import java.util.*

object DateUtil {

    fun initTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"))
    }

}