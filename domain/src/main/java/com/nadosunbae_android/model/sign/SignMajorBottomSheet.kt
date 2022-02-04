package com.nadosunbae_android.model.sign

//회원가입시 사용되는 bottomsheet
data class SignMajorBottomSheet(
    val isFirstMajor: Boolean,
    val isSecondMajor: Boolean,
    val majorId: Int,
    val majorName: String
)
