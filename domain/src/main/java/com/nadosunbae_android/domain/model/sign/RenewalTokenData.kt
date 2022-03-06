package com.nadosunbae_android.domain.model.sign

data class RenewalTokenData(
    val status: Int,
    val success: Boolean,
    val accessToken: String,
    val refreshToken: String,
    val user: SignInData.User
)