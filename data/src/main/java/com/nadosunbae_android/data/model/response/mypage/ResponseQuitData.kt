package com.nadosunbae_android.data.model.response.mypage

data class ResponseQuitData(
    val data: Data,
    val message: String = "",
    val status: Int = 0,
    val success: Boolean = true
) {
    data class Data(
        val comment: Comment,
        val block: Block,
        val classroomPost: ClassroomPost,
        val like: Like,
        val notification: Notification,
        val report: Report,
        val reviewPost: ReviewPost,
        val user: User
    ) {
        data class Comment(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class Block(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class ClassroomPost(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class Like(
            val id: Int = 0,
            val isLiked: Boolean = true,
            val updatedAt: String = ""
        )

        data class Notification(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class Report(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class ReviewPost(
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )

        data class User(
            val email: String = "",
            val id: Int = 0,
            val isDeleted: Boolean = true,
            val updatedAt: String = ""
        )
    }
}