package com.nadosunbae_android.data.model.request.favorites

import com.nadosunbae_android.domain.model.favorites.FavoritesParam

data class RequestFavoritesData(
    val majorId : Int
)

fun FavoritesParam.toEntity() : RequestFavoritesData{
    return RequestFavoritesData(
        majorId = this.majorId
    )
}
