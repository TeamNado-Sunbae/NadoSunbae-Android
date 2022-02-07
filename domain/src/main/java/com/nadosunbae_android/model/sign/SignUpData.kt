package com.nadosunbae_android.model.sign

//회원가입 RequestData
data class SignUpData(
    val email : String,
    val nickname : String,
    val password : String,
    val universityId : Int,
    val firstMajorId : Int,
    val firstMajorStart : String,
    val secondMajorId : Int,
    val secondMajorStart : String
)
