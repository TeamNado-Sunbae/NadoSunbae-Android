package com.nadosunbae_android.data.model.response.user

data class ResponseSeniorList(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: SeniorData
) {
    data class SeniorData(
        val onQuestionUser: List<UserSummaryData>,
        val offQuestionUser: List<UserSummaryData>
    )

    data class UserSummaryData(
        val id: Int,
        val profileImageId: Int,
        val isFirstMajor: Boolean,
        val isOnQuestion: Boolean,
        val majorStart: String,
        val nickname: String,
        val rate: Int
    )
}
