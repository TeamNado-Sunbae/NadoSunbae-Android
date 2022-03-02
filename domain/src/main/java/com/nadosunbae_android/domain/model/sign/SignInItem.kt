package com.nadosunbae_android.domain.model.sign

//회원가입 RequestData
data class SignInItem(
    val email : String,
    val password : String,
    val deviceToken : String
)
