package com.nadosunbae_android.repository.like

import com.nadosunbae_android.model.like.LikeData
import com.nadosunbae_android.model.like.LikeItem

interface LikeRepository {

    // 좋아요
    suspend fun postLike(likeItem: LikeItem): LikeData
}