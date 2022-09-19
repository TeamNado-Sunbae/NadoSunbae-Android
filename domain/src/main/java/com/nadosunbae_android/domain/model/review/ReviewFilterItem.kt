package com.nadosunbae_android.domain.model.review

data class ReviewFilterItem(
    val majorId: Int,
    val writerFilter: String,
    val tagFilter: List<Int>
)
