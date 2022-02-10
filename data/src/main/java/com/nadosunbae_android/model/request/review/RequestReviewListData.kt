package com.nadosunbae_android.model.request.review

data class RequestReviewListData(
    val majorId: Int,
    val writerFilter: Int,
    val tagFilter: List<Int>
)
