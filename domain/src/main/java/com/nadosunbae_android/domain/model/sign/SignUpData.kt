package com.nadosunbae_android.domain.model.sign


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
