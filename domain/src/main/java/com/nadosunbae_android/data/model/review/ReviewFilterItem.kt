package com.nadosunbae_android.data.model.review

data class ReviewFilterItem(
    val majorId: Int,
    val writerFilter: Int,
    val tagFilter: List<Int>
)
