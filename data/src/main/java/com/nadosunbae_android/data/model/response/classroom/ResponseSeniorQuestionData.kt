package com.nadosunbae_android.data.model.response.classroom

import java.util.*

data class ResponseSeniorQuestionData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val classroomPostList: List<ClassroomPost>
    ) {
        data class ClassroomPost(
            val commentCount: String,
            val content: String,
            val createdAt: Date?,
            val likeCount: String,
            val postId: Int,
            val title: String,
            val writer: Writer
        ) {
            data class Writer(
                val nickname: String,
                val profileImageId: Int,
                val writerId: Int
            )
        }
    }
}