package com.nadosunbae_android.datasource.remote.like

import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class LikeDataSourceImpl : LikeDataSource {
    override fun postLike(
        requestBody: RequestPostLike,
        onResponse: (Response<ResponsePostLike>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.likeService.postLike(requestBody).enqueueUtil(
            onResponse, onFailure
        )
    }

}