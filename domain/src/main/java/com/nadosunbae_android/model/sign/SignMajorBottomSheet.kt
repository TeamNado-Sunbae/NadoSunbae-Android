package com.nadosunbae_android.model.sign

//회원가입시 사용되는 bottomsheet
data class SignMajorBottomSheet(
    val data: List<Data>
) {
    data class Data(
        val majorId: Int,
        val majorName: String,
        val isFirstMajor: Boolean,
        val isSecondMajor: Boolean
    )
}

