package com.nadosunbae_android.model.response.classroom

data class ResponseClassRoomWriteData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val post: Post,
        val writer: Writer
    ) {
        data class Post(
            val content: String,
            val createdAt: String,
            val postId: Int,
            val title: String
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