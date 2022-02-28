package com.nadosunbae_android.domain.model.mypage

data class MyPageBlockData(
    val data: List<Data>,
    val success: Boolean
) {
    data class Data(
        val nickname: String,
        val profileImageId: Int,
        val userId: Int
    )
}
