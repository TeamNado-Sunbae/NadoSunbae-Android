package com.nadosunbae_android.domain.model.mypage

data class MyPageQuitData(
    val data: Data,
    val status: Int = 0,
    val success: Boolean = true
) {
    data class Data(
        val comment: List<Comment>,
        val block: List<Block>,
        val classroomPost: List<ClassroomPost>,
        val like: List<Like>,
        val notification: List<Notification>,
        val report: List<Report>,
        val reviewPost: List<ReviewPost>,
        val user: User
    ) {
        data class Comment(
            val id: Int = 0 ,
            val isDeleted: Boolean = true,
            val updatedAt: String =""
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
