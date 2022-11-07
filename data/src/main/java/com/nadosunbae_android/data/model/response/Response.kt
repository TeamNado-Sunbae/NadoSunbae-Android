package com.nadosunbae_android.data.model.response

data class Response<T>(
    val status: Int,
    val message: String,
    val success: Boolean,
    val data: T
)
