package com.nadosunbae_android.model.sign

data class SignBottomSheetItem(
    val data: List<Data>,
    val success: Boolean
) {
    data class Data(
        val isFirstMajor: Boolean,
        val isSecondMajor: Boolean,
        val majorId: Int,
        val majorName: String
    )
}
