package com.nadosunbae_android.domain.model.community

import java.util.*

data class CommunityMainData(
    val commentCount: Int,
    val content: String,
    val createdAt: Date?,
    val majorName: String,
    val postId: Int,
    val title: String,
    val type: String,
    val isLiked: Boolean,
    val likeCount: Int,
    val id: Int,
    val nickname: String
) {
    companion object {
        val DEFAULT = CommunityMainData(
            0,
            "",
            null,
            "",
            0,
            "",
            "",
            false,
            0,
            0,
            ""
        )

    }
}
