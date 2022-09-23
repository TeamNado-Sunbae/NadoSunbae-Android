package com.nadosunbae_android.data.model.request.post

import com.nadosunbae_android.domain.model.post.PostUpdateParam

data class RequestPostUpdateData(
    val title : String,
    val content : String?
)

fun PostUpdateParam.toEntity() : RequestPostUpdateData{
    return RequestPostUpdateData(
        title = this.title,
        content = this.content
    )
}