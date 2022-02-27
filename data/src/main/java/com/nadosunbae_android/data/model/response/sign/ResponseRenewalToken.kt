package com.nadosunbae_android.data.model.response.sign

data class ResponseRenewalToken(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val accesstoken: String
    )
}