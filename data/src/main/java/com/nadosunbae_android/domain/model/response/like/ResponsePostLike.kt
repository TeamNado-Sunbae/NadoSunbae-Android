package com.nadosunbae_android.domain.model.response.like

data class ResponsePostLike(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isLiked: Boolean,
        val postId: Int
    )
}