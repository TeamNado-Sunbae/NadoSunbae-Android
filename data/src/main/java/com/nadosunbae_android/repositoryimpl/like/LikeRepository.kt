package com.nadosunbae_android.repositoryimpl.like

import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike
import retrofit2.Response

interface LikeRepository {

    fun postLike(requestBody: RequestPostLike,
                 onResponse: (Response<ResponsePostLike>) -> Unit,
                 onFailure: (Throwable) -> Unit)
}