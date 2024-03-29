package com.nadosunbae_android.data.model.response.classroom

import java.util.*

data class ResponseClassRoomMainData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Data>,
) {
    data class Data(
        val postId: Int,
        val title: String,
        val content: String,
        val createdAt: Date?,
        val writer: Writer,
        val like : Like,
        val commentCount: Int
    ) {
        data class Writer(
            val nickname: String,
            val profileImageId: Int,
            val writerId: Int
        )
        data class Like(
            val isLiked : Boolean,
            val likeCount : Int
        )
    }
}