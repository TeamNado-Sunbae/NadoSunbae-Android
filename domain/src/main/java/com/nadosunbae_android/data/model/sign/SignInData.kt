package com.nadosunbae_android.data.model.sign

//회원가입 RequestData
data class SignInData(
    val email : String,
    val password : String,
    val deviceToken : String
)
