package com.nadosunbae_android.data.model.response.home

import java.util.*

data class ResponseUnivReview(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val createdAt: Date,
        val id: Int,
        val like: Like,
        val majorName: String,
        val oneLineReview: String,
        val tagList: List<Tag>
    ) {
        data class Like(
            val isLiked: Boolean,
            val likeCount: Int
        )

        data class Tag(
            val tagName: String
        )
    }
}