package com.nadosunbae_android.data.model.sign

data class MajorData(
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
