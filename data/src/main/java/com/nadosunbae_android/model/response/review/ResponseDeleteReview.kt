package com.nadosunbae_android.data.model.response.review

data class ResponseDeleteReview(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isDeleted: Boolean,
        val postId: String
    )
}