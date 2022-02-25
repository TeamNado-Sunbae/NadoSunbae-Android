package com.nadosunbae_android.data.model.response.classroom

data class ResponseWriteUpdateData(
    val data : Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val like: Like,
        val post: Post,
        val writer: Writer
    ) {
        data class Like(
            val isLiked: Boolean,
            val likeCount: Int
        )

        data class Post(
            val content: String,
            val createdAt: String,
            val postId: Int,
            val title: String,
            val updatedAt: String
        )

        data class Writer(
            val firstMajorName: String,
            val firstMajorStart: String,
            val nickname: String,
            val profileImageId: Int,
            val secondMajorName: String,
            val secondMajorStart: String,
            val writerId: Int
        )
    }
}