package com.nadosunbae_android.data.model.response.app

data class ResponseAppBanner(
    val data: List<String>,
    val message: String,
    val status: Int,
    val success: Boolean
)