package com.nadosunbae_android.datasource.remote.like

import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike
import retrofit2.Response

interface LikeDataSource {

    fun postLike(
        requestBody: RequestPostLike,
        onResponse: (Response<ResponsePostLike>) -> Unit,
        onFailure: (Throwable) -> Unit
    )
}