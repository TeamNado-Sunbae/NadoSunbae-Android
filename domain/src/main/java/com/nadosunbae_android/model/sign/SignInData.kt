package com.nadosunbae_android.model.sign

//회원가입 RequestData
data class SignInData(
    val email : String,
    val password : String,
    val deviceToken : String
)
