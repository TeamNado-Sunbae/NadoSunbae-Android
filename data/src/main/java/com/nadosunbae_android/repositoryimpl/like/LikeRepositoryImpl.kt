package com.nadosunbae_android.repositoryimpl.like

import com.nadosunbae_android.datasource.remote.like.LikeDataSource
import com.nadosunbae_android.datasource.remote.like.LikeDataSourceImpl
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike
import retrofit2.Response

class LikeRepositoryImpl : LikeRepository {
    val likeDataSource: LikeDataSource = LikeDataSourceImpl()

    override fun postLike(
        requestBody: RequestPostLike,
        onResponse: (Response<ResponsePostLike>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return likeDataSource.postLike(requestBody, onResponse, onFailure)
    }
}