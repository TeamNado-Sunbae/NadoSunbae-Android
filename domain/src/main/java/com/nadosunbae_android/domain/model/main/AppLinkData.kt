package com.nadosunbae_android.domain.model.main

data class AppLinkData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val kakaoTalkChannel: String,
        val openSourceLicense: String,
        val personalInformationPolicy: String,
        val termsOfService: String
    )
}
