package com.nadosunbae_android.domain.repository.like

import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem

interface LikeRepository {

    // 좋아요
    suspend fun postLike(likeItem: LikeItem): LikeData
}