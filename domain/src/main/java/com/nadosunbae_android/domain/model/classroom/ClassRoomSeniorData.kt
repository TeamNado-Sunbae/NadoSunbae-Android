package com.nadosunbae_android.domain.model.classroom

data class ClassRoomSeniorData(
    val userSummaryDataList: List<UserSummaryData>,
    val onQuestionUserList: List<UserSummaryData>
) {
    data class UserSummaryData(
        val profileImageId: Int,
        val isFirstMajor: Boolean,
        val isOnQuestion: Boolean,
        val majorStart: String,
        val nickname: String,
        val rate: Int,
        val id: Int
    )
}

