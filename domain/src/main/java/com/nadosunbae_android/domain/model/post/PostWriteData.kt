package com.nadosunbae_android.domain.model.post

data class PostWriteData(
    val answererId: Int,
    val content: String,
    val createdAt: String,
    val postId: Int,
    val majorId: Int,
    val title: String,
    val type: String,
    val firstMajorName: String,
    val firstMajorStart: String,
    val writeId: Int,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String
)

data class PostWriteParam(
    val type : String,
    val majorId : String?,
    val answerId : String,
    val title : String,
    val content : String
)
