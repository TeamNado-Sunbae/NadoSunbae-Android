package com.nadosunbae_android.data.model.request.review

data class RequestReviewListData(
    val majorId: Int,
    val writerFilter: String,
    val tagFilter: List<Int>
)
