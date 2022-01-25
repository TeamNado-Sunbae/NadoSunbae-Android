package com.nadosunbae_android.model.response.classroom

data class ResponseClassRoomSeniorData(
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
            val profileImageId : Int,
            val isFirstMajor: Boolean,
            val isOnQuestion: Boolean,
            val majorStart: String,
            val nickname: String,
            val userId: Int
        )

        data class OnQuestionUser(
            val profileImageId : Int,
            val isFirstMajor: Boolean,
            val isOnQuestion: Boolean,
            val majorStart: String,
            val nickname: String,
            val userId: Int
        )
    }
}