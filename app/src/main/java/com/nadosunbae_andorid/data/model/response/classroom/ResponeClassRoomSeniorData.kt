package com.nadosunbae_andorid.data.model.response.classroom

data class ResponeClassRoomSeniorData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val offQuestionUserList: List<OffQuestionUser>,
        val onQuestionUserList: List<OnQuestionUser>
    ) {
        data class OffQuestionUser(
            val isFirstMajor: Boolean,
            val isOnQuestion: Boolean,
            val majorStart: String,
            val nickname: String,
            val userId: Int
        )

        data class OnQuestionUser(
            val isFirstMajor: Boolean,
            val isOnQuestion: Boolean,
            val majorStart: String,
            val nickname: String,
            val userId: Int
        )
    }
}