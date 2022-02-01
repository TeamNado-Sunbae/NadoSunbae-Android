package com.nadosunbae_android.repository.like

import com.nadosunbae_android.model.request.like.RequestPostLike

interface LikeRepository {

    suspend fun postLike(requestBody: RequestPostLike)
}