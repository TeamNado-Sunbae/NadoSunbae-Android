package com.nadosunbae_android.domain.model.comment

import java.util.*

data class CommentData(
    val commentId: Int,
    val content: String,
    val createdAt: Date?,
    val isDeleted: Boolean,
    val postId: Int,
    val firstMajorName: String,
    val firstMajorStart: String,
    val nickname: String,
    val profileImageId: Int,
    val secondMajorName: String,
    val secondMajorStart: String,
    val writerId: Int
){
    companion object{
        val DEFAULT = CommentData(
            0,"",null,
            false,0,"","","",
            0,"","",0
        )
    }
}

data class CommentParam(
    val postId: String,
    val content: String
)




