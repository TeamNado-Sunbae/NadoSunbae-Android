package com.nadosunbae_android.data.model.response.mypage

data class ResponseMyPageBlock(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val nickname: String,
        val profileImageId: Int,
        val id: Int
    )
}