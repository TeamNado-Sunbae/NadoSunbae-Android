package com.nadosunbae_android.data.model.response.community

data class ResponseCommunityMainData(
    val commentCount: Int,
    val content: String,
    val createdAt: String,
    val like: Like,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String,
    val writer: Writer
) {
    data class Like(
        val isLiked: Boolean,
        val likeCount: Int
    )
    data class Writer(
        val id: Int,
        val nickname: String
    )
}
