package com.nadosunbae_andorid.data.model.sign

data class ResponseMajorData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val majorList: List<Major>
    ) {
        data class Major(
            val majorId: Int,
            val majorName: String
        )
    }
}