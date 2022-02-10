package com.nadosunbae_android.model.request.sign

data class RequestSignIn(
    val email : String,
    val password: String,
    val deviceToken : String
)
