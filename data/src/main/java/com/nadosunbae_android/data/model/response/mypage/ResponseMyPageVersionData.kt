package com.nadosunbae_android.data.model.response.mypage

data class ResponseMyPageVersionData(
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