package com.nadosunbae_android.data.repositoryimpl.like

import com.nadosunbae_android.data.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.data.model.request.like.toEntity
import com.nadosunbae_android.data.model.response.like.toEntity
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeParam
import com.nadosunbae_android.domain.repository.like.LikeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val dataSource: LikeDataSource
) : LikeRepository {

    override fun postLike(likeItem: LikeParam): Flow<LikeData> {
        return flow {
            emit(dataSource.postLike(likeItem.toEntity())
                .data.toEntity())
        }.flowOn(Dispatchers.IO)
    }

}