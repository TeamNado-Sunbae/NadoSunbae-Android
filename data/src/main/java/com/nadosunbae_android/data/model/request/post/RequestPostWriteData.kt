package com.nadosunbae_android.data.model.request.post

import com.nadosunbae_android.domain.model.post.PostWriteParam

data class RequestPostWriteData(
    val type: String,
    val majorId: String?,
    val answerId: String,
    val title: String,
    val content: String
)

fun PostWriteParam.toEntity(): RequestPostWriteData {
    return RequestPostWriteData(
        type = this.type,
        majorId = this.majorId,
        answerId = this.answerId,
        title = this.title,
        content = this.content
    )
}

