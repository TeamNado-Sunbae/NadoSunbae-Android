package com.nadosunbae_android.data.model.response.favorites

import com.nadosunbae_android.domain.model.favorites.FavoritesData

data class ResponseFavoritesData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isDeleted: Boolean,
        val majorId: Int,
        val userId: Int
    )
}

fun ResponseFavoritesData.toEntity() : FavoritesData{
    return FavoritesData(
        success = this.success,
        isDeleted = this.data.isDeleted,
        majorId = this.data.majorId,
        userId = this.data.userId
    )
}