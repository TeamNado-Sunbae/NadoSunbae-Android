package com.nadosunbae_android.data.model.request.classroom

data class RequestClassRoomPostData(
    val majorId : Int,
    val answerId : Int?,
    val postTypeId : Int,
    val title : String,
    val content : String
)
