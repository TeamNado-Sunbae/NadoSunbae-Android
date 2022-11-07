package com.nadosunbae_android.data.model.response.app

import com.nadosunbae_android.domain.model.app.AppVersionData

data class ResponseAppVersion(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val AOS: String,
        val iOS: String
    )
}

fun ResponseAppVersion.toEntity() : AppVersionData{
    return AppVersionData(
        AOS = this.data.AOS
    )
}