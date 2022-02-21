package com.nadosunbae_android.domain.model.mypage

data class MyPageVersionData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val AOS: String
    )
}