package com.nadosunbae_android.data.model.response.post

import com.nadosunbae_android.domain.model.post.PostDeleteData

data class ResponsePostDeleteData(
    val isDeleted: Boolean,
    val postId: Int
)

fun ResponsePostDeleteData.toEntity() : PostDeleteData{
    return PostDeleteData(
        isDeleted = isDeleted,
        postId = postId
    )
}