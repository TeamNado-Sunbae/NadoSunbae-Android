package com.nadosunbae_android.data.repository.like

import com.nadosunbae_android.data.model.request.like.RequestPostLike
import com.nadosunbae_android.data.model.response.like.ResponsePostLike
import com.nadosunbae_android.data.model.response.main.ResponseMajorListData
import retrofit2.Response

interface LikeRepository {

    fun postLike(requestBody: RequestPostLike,
                 onResponse: (Response<ResponsePostLike>) -> Unit,
                 onFailure: (Throwable) -> Unit)
}