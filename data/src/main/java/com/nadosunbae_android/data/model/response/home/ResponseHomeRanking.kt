package com.nadosunbae_android.data.model.response.home

data class ResponseHomeRanking(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val userList: List<User>
    ) {
        data class User(
            val firstMajorName: String,
            val firstMajorStart: String,
            val id: Int,
            val nickname: String,
            val profileImageId: Int,
            val rate: Int?,
            val secondMajorName: String,
            val secondMajorStart: String
        )
    }
}