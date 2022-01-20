package com.nadosunbae_android.data.model.ui.classroom

import java.util.*

data class ClassRoomData(
    val postId: Int,
    val title: String,
    val content: String,
    val createdAt: Date?,
    val writer: Writer,
    val likeCount: String,
    val commentCount: String
) {
    data class Writer(
        val nickname: String,
        val profileImageId: Int,
        val writerId: Int
    )
}
