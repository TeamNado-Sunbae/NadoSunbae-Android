package com.nadosunbae_android.model.classroom

data class ClassRoomSeniorData(
    val offQuestionUserList: List<OffQuestionUser>,
    val onQuestionUserList: List<OnQuestionUser>
) {
    data class OffQuestionUser(
        val profileImageId: Int,
        val isFirstMajor: Boolean,
        val isOnQuestion: Boolean,
        val majorStart: String,
        val nickname: String,
        val userId: Int
    )

    data class OnQuestionUser(
        val profileImageId: Int,
        val isFirstMajor: Boolean,
        val isOnQuestion: Boolean,
        val majorStart: String,
        val nickname: String,
        val userId: Int
    )
}

