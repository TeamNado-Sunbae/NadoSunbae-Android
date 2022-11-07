package com.nadosunbae_android.data.model.response.app

data class ResponseAppBanner(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val imageUrl: String,
        val redirectUrl: String
    )
}