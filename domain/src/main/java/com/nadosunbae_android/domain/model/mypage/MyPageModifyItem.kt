package com.nadosunbae_android.domain.model.mypage

data class MyPageModifyItem(
    var nickname : String,
    var firstMajorId : Int,
    var firstMajorStart : String,
    var secondMajorId : Int,
    var secondMajorStart : String,
    var isOnQuestion : Boolean
)
