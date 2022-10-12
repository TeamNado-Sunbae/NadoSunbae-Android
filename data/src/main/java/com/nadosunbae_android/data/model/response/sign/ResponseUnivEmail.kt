package com.nadosunbae_android.data.model.response.sign

data class ResponseUnivEmail(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val email: String
    )
}