package com.nadosunbae_android.data.repositoryimpl.like

import com.nadosunbae_android.data.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.data.mapper.like.LikeMapper
import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.repository.like.LikeRepository
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val dataSource: LikeDataSource
) : LikeRepository {

    override suspend fun postLike(likeItem: LikeItem): LikeData {
        return LikeMapper.mapperToLikeData(dataSource.postLike(
                LikeMapper.mapperToLikeItem(likeItem)
            ))
    }

}