package com.nadosunbae_android.model.request.sign

data class RequestSignUp(
    var email : String,
    var nickname : String,
    var password : String,
    val universityId : Int,
    var firstMajorId : Int,
    var firstMajorStart : String,
    var secondMajorId : Int,
    var secondMajorStart : String
)
