package com.nadosunbae_android.domain.model.mypage

import java.util.*

data class MyPagePostData(
    val data: Data,
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