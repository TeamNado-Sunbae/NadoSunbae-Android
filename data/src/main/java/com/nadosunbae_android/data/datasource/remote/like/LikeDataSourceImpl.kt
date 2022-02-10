package com.nadosunbae_android.data.datasource.remote.like

import com.nadosunbae_android.data.api.like.LikeService
import com.nadosunbae_android.data.model.request.like.RequestPostLike
import com.nadosunbae_android.data.model.response.like.ResponsePostLike

class LikeDataSourceImpl(private val service : LikeService) : LikeDataSource {
    override suspend fun postLike(requestBody: RequestPostLike): ResponsePostLike {
        return service.postLike(requestBody)
    }
}