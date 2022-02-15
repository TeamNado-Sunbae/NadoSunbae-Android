package com.nadosunbae_android.domain.model.sign

//이메일 중복확인 ResponseData
data class EmailDuplicationCheck(
    val status: Int,
    val success: Boolean
)