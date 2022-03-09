package com.nadosunbae_android.domain.model.network

data class ErrorBody(
    val status : Int,
    val success : Boolean,
    val message : String
)
