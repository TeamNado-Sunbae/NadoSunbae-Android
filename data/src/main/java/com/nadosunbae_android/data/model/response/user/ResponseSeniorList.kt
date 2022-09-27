package com.nadosunbae_android.data.model.response.user

data class ResponseSeniorList(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val onQuestionUserList: List<UserSummaryData>,
        val offQuestionUserList: List<UserSummaryData>
    ) {
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
}
