package com.nadosunbae_android.domain.repository.like

import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeParam
import kotlinx.coroutines.flow.Flow

interface LikeRepository {
    // 좋아요
    fun postLike(likeItem: LikeParam): Flow<LikeData>
}