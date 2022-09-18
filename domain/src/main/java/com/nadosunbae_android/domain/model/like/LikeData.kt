package com.nadosunbae_android.domain.model.like

data class LikeData(
    val isLiked: Boolean,
    val postId: Int
)

data class LikeParam(
    val targetId : String,
    val type : String
)
