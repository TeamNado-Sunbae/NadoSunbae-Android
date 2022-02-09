package com.nadosunbae_android.domain.model.classroom

data class ClassRoomPostWriteItem(
    val majorId : Int,
    val answererId : Int?,
    val postTypeId : Int,
    val title : String,
    val content : String
)
