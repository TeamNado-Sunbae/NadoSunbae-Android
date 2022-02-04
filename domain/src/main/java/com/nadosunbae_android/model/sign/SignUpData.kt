package com.nadosunbae_android.model.sign

//회원가입
data class SignUpData(
    val success : Boolean,
    val userId: Int,
    val accesstoken: String
)