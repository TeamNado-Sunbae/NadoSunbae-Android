package com.nadosunbae_andorid.data.model.review

data class PreviewData (
    val create_at: String,
    val likes: Int,
    val one_line_review: String,
    val content_list: List<String>,
    val nickname: String,
    val first_major: String,
    val second_major: String
)
