package com.nadosunbae_android.data.repositoryimpl.favorites

import com.nadosunbae_android.data.datasource.remote.favorites.FavoritesDataSource
import com.nadosunbae_android.data.model.request.favorites.toEntity
import com.nadosunbae_android.data.model.response.favorites.toEntity
import com.nadosunbae_android.domain.model.favorites.FavoritesData
import com.nadosunbae_android.domain.model.favorites.FavoritesParam
import com.nadosunbae_android.domain.repository.favorites.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val dataSource : FavoritesDataSource) : FavoritesRepository {
    override fun postFavorites(favoritesParam: FavoritesParam): Flow<FavoritesData> {
        return flow {
            emit(dataSource.postFavorites(favoritesParam.toEntity()).toEntity())
        }.flowOn(Dispatchers.IO)
    }
}