package com.nadosunbae_android.data.model.response.major

data class ResponseMajorListData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Data>
) {
    data class Data(
        val isFirstMajor: Boolean,
        val isSecondMajor: Boolean,
        val majorId: Int,
        val majorName: String
    )
}