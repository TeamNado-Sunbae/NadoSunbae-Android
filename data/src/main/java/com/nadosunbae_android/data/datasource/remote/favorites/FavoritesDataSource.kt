package com.nadosunbae_android.data.datasource.remote.favorites

import com.nadosunbae_android.data.model.request.favorites.RequestFavoritesData
import com.nadosunbae_android.data.model.response.favorites.ResponseFavoritesData

interface FavoritesDataSource {

    //학과 즐겨찾기
    suspend fun postFavorites(requestFavoritesData: RequestFavoritesData) : ResponseFavoritesData
}