package com.nadosunbae_android.domain.model.user

data class SeniorListData(
    val onQuestionList: List<UserUnitData>,
    val offQuestionList: List<UserUnitData>
) {
    data class UserUnitData(
        val id: Int,
        val profileImageId: Int,
        val isFirstMajor: Boolean,
        val isOnQuestion: Boolean,
        val majorStart: String,
        val nickname: String,
        val rate: Int
    )
}
