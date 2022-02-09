package com.nadosunbae_android.domain.model.response.sign

data class ResponseSignNickname(
    val data: String,
    val message: String,
    val status: Int,
    val success: Boolean
)