package com.nadosunbae_android.data.model.response.mypage

import java.util.*

data class ResponseMyPagePostData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val classroomPostList: List<ClassroomPost>
    ) {
        data class ClassroomPost(
            val commentCount: Int,
            val content: String,
            val createdAt: Date?,
            val like: Like,
            val majorName: String,
            val postId: Int,
            val postTypeId: Int,
            val title: String
        ) {
            data class Like(
                val isLiked: Boolean,
                val likeCount: Int
            )
        }
    }
}