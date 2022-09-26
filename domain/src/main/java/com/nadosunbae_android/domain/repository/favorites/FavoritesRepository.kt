package com.nadosunbae_android.domain.repository.favorites

import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    //학과 즐겨찾기
    fun postFavorites(favoritesParam: FavoritesParam) : Flow<FavoritesData>
}