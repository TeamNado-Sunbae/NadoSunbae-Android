package com.nadosunbae_android.data.model.response.mypage

import java.util.*

data class ResponseMyPageReplyData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val classroomPostListByMyCommentList: List<ClassroomPostListByMyComment>
    ) {
        data class ClassroomPostListByMyComment(
            val commentCount: Int,
            val content: String,
            val createdAt: Date?,
            val like: Like,
            val postId: Int,
            val title: String,
            val writer: Writer
        ) {
            data class Like(
                val isLiked: Boolean,
                val likeCount: Int
            )

            data class Writer(
                val nickname: String,
                val profileImageId: Int,
                val writerId: Int
            )
        }
    }
}