package com.nadosunbae_android.data.datasource.remote.favorites

import com.nadosunbae_android.data.api.favorites.FavoritesService
import com.nadosunbae_android.data.model.request.favorites.RequestFavoritesData
import com.nadosunbae_android.data.model.response.favorites.ResponseFavoritesData
import javax.inject.Inject

class FavoritesDataSourceImpl @Inject constructor(private val service : FavoritesService) : FavoritesDataSource {
    override suspend fun postFavorites(requestFavoritesData: RequestFavoritesData): ResponseFavoritesData {
        return service.postFavorites(requestFavoritesData)
    }
}