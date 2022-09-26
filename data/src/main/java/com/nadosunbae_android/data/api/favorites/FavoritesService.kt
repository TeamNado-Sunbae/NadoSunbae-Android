package com.nadosunbae_android.data.api.favorites

import com.nadosunbae_android.data.model.request.favorites.RequestFavoritesData
import com.nadosunbae_android.data.model.response.favorites.ResponseFavoritesData
import retrofit2.http.Body
import retrofit2.http.POST

interface FavoritesService {
    //학과 즐겨찾기
    @POST("favorites")
    suspend fun postFavorites(
        @Body requestFavoritesData: RequestFavoritesData
    ) : ResponseFavoritesData
}