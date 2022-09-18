package com.nadosunbae_android.data.datasource.remote.like

import com.nadosunbae_android.data.model.request.like.RequestPostLike
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.like.ResponsePostLike

interface LikeDataSource {

    suspend fun postLike(requestBody: RequestPostLike) : Response<ResponsePostLike>
}