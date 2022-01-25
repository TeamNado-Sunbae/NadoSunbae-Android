package com.nadosunbae_android.model.response.review

data class ResponseBackgroundImageListData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val backgroundImageList: List<BackgroundImage>
    ) {
        data class BackgroundImage(
            val imageId: Int,
            val imageUrl: String
        )
    }
}