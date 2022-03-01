package com.nadosunbae_android.data.model.response.mypage

data class ResponseMyPageAppLink(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val kakaoTalkChannel: String,
        val openSourceLicense: String,
        val personalInformationPolicy: String,
        val termsOfService: String
    )
}