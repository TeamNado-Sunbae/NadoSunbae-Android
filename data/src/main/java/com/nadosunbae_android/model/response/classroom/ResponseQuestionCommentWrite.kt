package com.nadosunbae_android.model.response.classroom

data class ResponseQuestionCommentWrite(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val commentId: Int,
        val content: String,
        val createdAt: String,
        val isDeleted: Boolean,
        val postId: Int,
        val writer: Writer
    ) {
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