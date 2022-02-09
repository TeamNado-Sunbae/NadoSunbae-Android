package com.nadosunbae_android.domain.model.sign

//닉네임 중복확인 ResponseData
data class NicknameDuplicationCheck(
    val status: Int,
    val success: Boolean
)