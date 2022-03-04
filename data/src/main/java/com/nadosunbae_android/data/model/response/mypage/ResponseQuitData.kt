package com.nadosunbae_android.data.model.response.mypage

data class ResponseQuitData(
    val data: Data,
    val message: String = "",
    val status: Int = 0,
    val success: Boolean = true
) {
    data class Data(
        val comment: List<Comments> = emptyList(),
        val block: List<Block> = emptyList(),
        val classroomPost: List<ClassroomPost> = emptyList(),
        val like: List<Like> = emptyList(),
        val notification: List<Notification> = emptyList(),
        val report: List<Report> = emptyList(),
        val reviewPost: List<ReviewPost> = emptyList(),
        val user: User
    ) {
        data class Comments(
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