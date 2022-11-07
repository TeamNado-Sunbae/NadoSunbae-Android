package com.nadosunbae_android.data.model.request.like

import com.nadosunbae_android.domain.model.like.LikeParam

data class RequestPostLike(
    val targetId: String,
    val type: String
)

fun LikeParam.toEntity() : RequestPostLike{
    return RequestPostLike(
        targetId = this.targetId,
        type = this.type
    )

}
