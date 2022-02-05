package com.nadosunbae_android.model.review

data class BackgroundImageListData(
    val backgroundImageList: List<BackgroundImage>
) {
    data class BackgroundImage(
        val imageId: Int,
        val imageUrl: String
    )
}

