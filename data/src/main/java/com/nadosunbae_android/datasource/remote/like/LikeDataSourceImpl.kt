package com.nadosunbae_android.datasource.remote.like

import com.nadosunbae_android.api.like.LikeService
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike

class LikeDataSourceImpl(private val service : LikeService) : LikeDataSource {
    override suspend fun postLike(requestBody: RequestPostLike): ResponsePostLike {
        return service.postLike(requestBody)
    }
}