package com.nadosunbae_android.domain.model.response.classroom

data class ResponseSeniorPersonalData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val firstMajorName: String,
        val firstMajorStart: String,
        val isOnQuestion: Boolean,
        val nickname: String,
        val profileImageId: Int,
        val secondMajorName: String,
        val secondMajorStart: String,
        val userId: Int
    )
}