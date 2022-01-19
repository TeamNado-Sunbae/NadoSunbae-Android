package com.nadosunbae_android.data.model.response

data class ResponseMajorList(
    val `data`: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isFirstMajor: Boolean,
        val isSecondMajor: Boolean,
        val majorId: Int,
        val majorName: String
    )
}