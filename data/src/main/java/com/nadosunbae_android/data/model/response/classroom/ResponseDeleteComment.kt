package com.nadosunbae_android.data.model.response.classroom

data class ResponseDeleteComment(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val commentId: Int,
        val isDeleted: Boolean
    )
}