package com.nadosunbae_android.domain.model.classroom

data class ClassRoomPostWriteData(
        val success: Boolean,
        val content: String,
        val createdAt: String,
        val postId: Int,
        val title: String,
        val firstMajorName: String,
        val firstMajorStart: String,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String,
        val writerId: Int
)
