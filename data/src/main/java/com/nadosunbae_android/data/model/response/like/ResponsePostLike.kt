package com.nadosunbae_android.data.model.response.like
import com.nadosunbae_android.domain.model.like.LikeData

data class ResponsePostLike(
    val isLiked: Boolean,
    val postId: Int
)

fun ResponsePostLike.toEntity(): LikeData {
    return LikeData(
        isLiked = this.isLiked,
        postId = this.postId
    )
}