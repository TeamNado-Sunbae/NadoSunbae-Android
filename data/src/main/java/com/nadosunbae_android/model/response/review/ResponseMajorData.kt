package com.nadosunbae_android.model.response.review

data class ResponseMajorData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val homepage: String,
        val majorName: String,
        val subjectTable: String
    )
}