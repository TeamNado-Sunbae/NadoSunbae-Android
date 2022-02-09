package com.nadosunbae_android.data.datasource.remote.like

import com.nadosunbae_android.domain.model.request.like.RequestPostLike
import com.nadosunbae_android.domain.model.response.like.ResponsePostLike

interface LikeDataSource {

    suspend fun postLike(requestBody: RequestPostLike) : ResponsePostLike
}