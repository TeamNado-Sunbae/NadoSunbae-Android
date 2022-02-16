package com.nadosunbae_android.domain.model.classroom

import java.util.*

data class ClassRoomData(
    val postId: Int,
    val title: String,
    val content: String,
    val createdAt: Date?,
    val writer: Writer,
    val likeCount: Int,
    val isLiked : Boolean,
    val commentCount: Int
) {
    data class Writer(
        val nickname: String,
        val profileImageId: Int,
        val writerId: Int
    )
}
