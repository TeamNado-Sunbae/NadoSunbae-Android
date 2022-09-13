package com.nadosunbae_android.data.model.request.mypage

data class RequestMyPageModify(
    var profileImageId: Int,
    var nickname : String,
    var bio : String,
    var firstMajorId : Int,
    var firstMajorStart : String,
    var secondMajorId : Int,
    var secondMajorStart : String,
    var isOnQuestion : Boolean
)
