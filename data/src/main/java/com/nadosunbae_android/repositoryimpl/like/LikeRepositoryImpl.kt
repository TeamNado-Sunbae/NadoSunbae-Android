package com.nadosunbae_android.repositoryimpl.like

import com.nadosunbae_android.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.mapper.like.LikeMapper
import com.nadosunbae_android.model.like.LikeData
import com.nadosunbae_android.model.like.LikeItem
import com.nadosunbae_android.repository.like.LikeRepository

class LikeRepositoryImpl(
    private val dataSource: LikeDataSource
) : LikeRepository {

    override suspend fun postLike(likeItem: LikeItem): LikeData {
        return LikeMapper.mapperToLikeData(dataSource.postLike(
                LikeMapper.mapperToLikeItem(likeItem)
            ))
    }

}